package com.baza.firmy.common.service;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.common.util.FileUtills;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.constants.enums.WojewodztwaRaportyEnum;
import com.baza.firmy.danezraportu.entity.RaportDto;
import com.baza.firmy.dto.JdgSzczegolyRaportDto;
import com.baza.firmy.jdg.domain.JdgFacade;
import com.baza.firmy.jdg.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.jdg.query.JdgQueryFacade;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PobierzDaneZRaportuService {

  private final FileUtills fileUtills;
  private final JdgFacade jdgFacade;
  private final JdgQueryFacade jdgQueryFacade;

  public void pobierzDaneZRaportu() {
    log.info("Zaczynam pobieranie danych z raportu");

    Arrays.stream(WojewodztwaRaportyEnum.values()).forEach(wojewodztwoRaport -> {
      try {
        List<JdgSzczegolyRaportDto> listaDzialalnosciWojewodztwa = unmarshalRaport(wojewodztwoRaport.getNazwaPlikuRaportu());

        listaDzialalnosciWojewodztwa.forEach(dzialalnosc -> {
          try {
            if (!czyIstniejeDzialalnoscWBazie(dzialalnosc)) {
              jdgFacade.stworzJdg(
                  stworzJdgSzczegolyDto(dzialalnosc, wojewodztwoRaport.name())
              );
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
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    });
    log.info("Zakończono pobieranie danych z raportu");
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
    if (dzialalnosc.getNip().isEmpty() && dzialalnosc.getNazwaPodmiotu().isEmpty()) {
      return true;
    }

    return jdgQueryFacade.existsByWlascicielNipAndNazwaAndDataRozpoczecia(
        dzialalnosc.getNip().orElse(null),
        dzialalnosc.getNazwaPodmiotu().orElse(null),
        dzialalnosc.getDataRozpoczeciaDzialalnosci().map(LocalDate::parse).orElse(null));
  }

  private JdgSzczegolyDto stworzJdgSzczegolyDto(JdgSzczegolyRaportDto dzialalnosc, String wojewodztwo) {
    return JdgSzczegolyDto.builder()
        .nazwa(dzialalnosc.getNazwaPodmiotu().map(nazwaPodmiotu -> {
          String nazwa = ""
           .trim();
          if (nazwaPodmiotu.substring(0,1).equalsIgnoreCase("-")) {
            nazwa = nazwaPodmiotu.substring(1);
          }
          return nazwa.trim();
        }).orElse(null))
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
        .pkdGlowny(dzialalnosc.getPkdGlowny().orElse(null))
        .pkd(dzialalnosc.getPkd())
        .rokPkd(dzialalnosc.getRokPkd().orElse(null))
        .telefon(dzialalnosc.getTelefon().orElse(null))
        .email(dzialalnosc.getEmail().orElse(null))
        .www(dzialalnosc.getAdresWWW().orElse(null))
        .status(BusinessStatus.getByRaportLabel(dzialalnosc.getStatusDzialalnosci()))
        .dataRozpoczecia(dzialalnosc.getDataRozpoczeciaDzialalnosci().orElse(null))
        .build();
  }
}
