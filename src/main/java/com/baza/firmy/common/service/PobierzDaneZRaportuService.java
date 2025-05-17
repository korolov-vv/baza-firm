package com.baza.firmy.common.service;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.common.util.FileUtills;
import com.baza.firmy.constants.enums.WojewodztwaRaportyEnum;
import com.baza.firmy.danezraportu.entity.RaportDto;
import com.baza.firmy.dto.JdgSzczegolyRaportDto;
import com.baza.firmy.jdg.domain.JdgFacade;
import com.baza.firmy.jdg.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
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

  public void pobierzDaneZRaportu() {
    log.info("Zaczynam pobieranie danych z raportu");

    Arrays.stream(WojewodztwaRaportyEnum.values()).forEach(wojewodztwoRaport -> {
      try {
        List<JdgSzczegolyRaportDto> listaDzialalnosciWojewodztwa = unmarshalRaport(wojewodztwoRaport.getNazwaPlikuRaportu());

        listaDzialalnosciWojewodztwa.forEach(d ->
            jdgFacade.stworzJdg(
                  JdgSzczegolyDto.builder()
                      .nazwa(d.getNazwaPodmiotu().orElse(null))
                      .adresKorespondencyjny(AdresDto.builder()
                          .ulica(d.getUlica().map(String::toUpperCase).orElse(null))
                          .budynek(d.getNrBudynku().map(String::toUpperCase).orElse(null))
                          .lokal(d.getNrLokalu().map(String::toUpperCase).orElse(null))
                          .miasto(d.getMiejscowosc().map(String::toUpperCase).orElse(null))
                          .wojewodztwo(wojewodztwoRaport.name())
                          .powiat(d.getPowiat().map(String::toUpperCase).orElse(null))
                          .gmina(d.getGmina().map(String::toUpperCase).orElse(null))
                          .kraj("POLSKA")
                          .kod(d.getKodPocztowy().map(String::toUpperCase).orElse(null))
                          .build())
                      .wlasciciel(StworzWlascicielaDto.builder()
                          .imie(d.getImie().orElse(null))
                          .nazwisko(d.getNazwisko().orElse(null))
                          .nip(d.getNip().orElse(null))
                          .regon(d.getRegon().orElse(null))
                          .build())
                      .pkdGlowny(d.getPkdGlowny().orElse(null))
                      .pkd(d.getPkd())
                      .rokPkd(d.getRokPkd().orElse(null))
                      .telefon(d.getTelefon().orElse(null))
                      .email(d.getEmail().orElse(null))
                      .www(d.getAdresWWW().orElse(null))
                      .dataRozpoczecia(d.getDataRozpoczeciaDzialalnosci().orElse(null))
                      .build()
              ));

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
}
