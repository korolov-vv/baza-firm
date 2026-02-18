package com.baza.firmy.common.service;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.common.util.FileUtills;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.constants.enums.WojewodztwaRaportyEnum;
import com.baza.firmy.danezraportu.entity.RaportDto;
import com.baza.firmy.dto.JdgSzczegolyRaportDto;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import com.baza.firmy.podmiotygospodarcze.domain.PodmiotyGospodarczeFacade;
import com.baza.firmy.podmiotygospodarcze.domain.Rejestr;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@RequiredArgsConstructor
public class PobierzDaneZRaportuExecutor {

  private final PodmiotyGospodarczeFacade podmiotyGospodarczeFacade;
  private final MailSenderService mailSenderService;
  private final FileUtills fileUtills;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;

  @Async ("pobierzDaneZRaportu")
  CompletableFuture<Void> zapiszDaneDlaWojewodztwa(WojewodztwaRaportyEnum wojewodztwoRaport) {
    try {
      log.info("Zaczynam pobieranie danych z raportu dla województwa: {}", wojewodztwoRaport.name());

      List<JdgSzczegolyRaportDto> listaDzialalnosciWojewodztwa = unmarshalRaport(wojewodztwoRaport.getNazwaPlikuRaportu());

      List<JdgSzczegolyRaportDto> listaDzialalnosci = listaDzialalnosciWojewodztwa;

      if (WojewodztwaRaportyEnum.BRAK_WOJEWODZTWA.equals(wojewodztwoRaport)) {
        listaDzialalnosci = listaDzialalnosciWojewodztwa.subList(0, listaDzialalnosciWojewodztwa.size());
      }

      AtomicLong liczbaZapisanychFirm = new AtomicLong(0L);
      listaDzialalnosci.forEach(dzialalnosc -> {
        try {
          if (!czyIstniejeDzialalnoscWBazie(dzialalnosc)) {
            podmiotyGospodarczeFacade.stworzPodmiotGospodarczy(
                stworzJdgSzczegolyDto(dzialalnosc, wojewodztwoRaport.name())
            );
            liczbaZapisanychFirm.getAndIncrement();
          } else {
            log.info("Skip JDG NIP: {} Nazwa: {} Data rozpoczęcia: {}",
                dzialalnosc.getNip().orElse(null),
                dzialalnosc.getNazwaPodmiotu().orElse(null),
                dzialalnosc.getDataRozpoczeciaDzialalnosci().orElse(null));
          }
        } catch (Exception e) {
          log.error("Błąd podczas przetwarzania JDG NIP: {} Nazwa: {} Data rozpoczęcia: {}",
              dzialalnosc.getNip().orElse(null),
              dzialalnosc.getNazwaPodmiotu().orElse(null),
              dzialalnosc.getDataRozpoczeciaDzialalnosci().orElse(null));
          log.error(e.getMessage());
        }
      });
      mailSenderService.sendEmailWithFirms(
          "vadymkorolov@gmail.com",
          "Zapisane JDG z " + wojewodztwoRaport.getNazwaPlikuRaportu(),
          String.format("Cześć! Zapisałem %s firm z %s", liczbaZapisanychFirm.get(), listaDzialalnosciWojewodztwa.size()));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return CompletableFuture.completedFuture(null);
  }

  private List<JdgSzczegolyRaportDto> unmarshalRaport(String nazwaPliku) throws JAXBException, XMLStreamException {
    List<JdgSzczegolyRaportDto> listaDzialalnosciGospodarczych = new ArrayList<>();
    File file = fileUtills.getFile("raporty/ceidg", nazwaPliku);

    JAXBContext jc = JAXBContext.newInstance(RaportDto.class);
    XMLInputFactory xif = XMLInputFactory.newFactory();
    StreamSource source = new StreamSource(file);
    XMLStreamReader xsr = xif.createXMLStreamReader(source);
    while (xsr.hasNext()) {
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      if (xsr.isStartElement() && "Detail".equals(xsr.getLocalName())) {
        listaDzialalnosciGospodarczych.add((JdgSzczegolyRaportDto) unmarshaller.unmarshal(xsr));
      }
      xsr.next();
    }

    return listaDzialalnosciGospodarczych;
  }

  private boolean czyIstniejeDzialalnoscWBazie(JdgSzczegolyRaportDto dzialalnosc) {
    if (dzialalnosc.getNip().isEmpty()) {
      return true;
    }

    return podmiotyGospodarczeQueryFacade.existsByNipAndDataRozpoczecia(
        dzialalnosc.getNip().orElse(null),
        dzialalnosc.getDataRozpoczeciaDzialalnosci().map(LocalDate::parse).orElse(null));
  }

  private JdgSzczegolyDto stworzJdgSzczegolyDto(JdgSzczegolyRaportDto dzialalnosc, String wojewodztwo) {
    return JdgSzczegolyDto.builder()
        .nazwa(dzialalnosc.getNazwaPodmiotu().map(nazwaPodmiotu -> {
          if (nazwaPodmiotu.substring(0,1).equalsIgnoreCase("-")) {
            return nazwaPodmiotu.substring(1).trim();
          }
          return nazwaPodmiotu.trim();
        }).orElse(null))
        .rejestr(Rejestr.CEIDG)
        .adresKorespondencyjny(AdresDto.builder()
            .ulica(dzialalnosc.getUlica().map(String::toUpperCase).orElse(null))
            .budynek(dzialalnosc.getNrBudynku().map(String::toUpperCase).orElse(null))
            .lokal(dzialalnosc.getNrLokalu().map(String::toUpperCase).orElse(null))
            .miasto(dzialalnosc.getMiejscowosc().map(String::toUpperCase).orElse(null))
            .wojewodztwo(wojewodztwo)
            .powiat(dzialalnosc.getPowiat().map(String::toUpperCase).orElse(null))
            .gmina(dzialalnosc.getGmina().map(String::toUpperCase).orElse(null))
            .kraj("POLSKA")
            .kod(dzialalnosc.getKodPocztowy().map(String::toUpperCase).orElse(null))
            .build())
        .wlasciciel(StworzWlascicielaDto.builder()
            .imie(dzialalnosc.getImie().orElse(null))
            .nazwisko(dzialalnosc.getNazwisko().orElse(null))
            .nip(dzialalnosc.getNip().orElse(null))
            .regon(dzialalnosc.getRegon().orElse(null))
            .build())
        .pkdGlowny(dzialalnosc.getPkdGlowny()
                .orElse(null))
        .pkd(dzialalnosc.getPkd().stream()
                .toList())
        .rokPkd(dzialalnosc.getRokPkd().orElse(null))
        .telefon(dzialalnosc.getTelefon().orElse(null))
        .email(dzialalnosc.getEmail().orElse(null))
        .www(dzialalnosc.getAdresWWW().orElse(null))
        .status(BusinessStatus.getByRaportLabel(dzialalnosc.getStatusDzialalnosci()))
        .dataRozpoczecia(dzialalnosc.getDataRozpoczeciaDzialalnosci().orElse(null))
        .build();
  }
}
