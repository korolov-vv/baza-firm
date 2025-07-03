package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.response.krs.DanePodmiotuResponse;
import com.baza.firmy.response.krs.Dzial1Response;
import com.baza.firmy.response.krs.Dzial2Response;
import com.baza.firmy.response.krs.Dzial3Response;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import com.baza.firmy.response.krs.OdpisResponse;
import com.baza.firmy.response.krs.SiedzibaIAdresResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = { UUID.class, Rejestr.class, Objects.class, Strings.class,
    OdpisAktualnyResponse.class, OdpisResponse.class, OdpisResponse.NaglowekAResponse.class, OdpisResponse.Wpis.class,
    Dzial1Response.class, Dzial2Response.class, Dzial3Response.class, DanePodmiotuResponse.class, SiedzibaIAdresResponse.class})
interface PodmiotyGospodarczeMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "wlasciciel", ignore = true)
  @Mapping(target = "wspolnicySpzoo", ignore = true)
  @Mapping(target = "reprezentacja", ignore = true) @Mapping(target = "adresDzialalnosci", ignore = true)
  @Mapping(target = "adresKorespondencyjny", ignore = true)
  @Mapping(target = "pkdGlowny", ignore = true)
  @Mapping(target = "pkd", ignore = true)
  @Mapping(target = "rejestr", defaultValue = "CEIDG")
  @Mapping(target = "nip", source = "wlasciciel.nip")
  @Mapping(target = "regon", ignore = true)
  @Mapping(target = "numerKrs", ignore = true)
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "nazwa", source = "nazwa", qualifiedByName = "setNazwa")
  PodmiotGospodarczeEntity toJdgEntity(JdgSzczegolyDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "wlasciciel", ignore = true)
  @Mapping(target = "wspolnicySpzoo", ignore = true)
  @Mapping(target = "reprezentacja", ignore = true)
  @Mapping(target = "adresDzialalnosci", ignore = true)
  @Mapping(target = "adresKorespondencyjny", ignore = true)
  @Mapping(target = "pkdGlowny", ignore = true)
  @Mapping(target = "pkd", ignore = true)
  @Mapping(target = "rejestr", defaultValue = "CEIDG")
  @Mapping(target = "nip", source = "wlasciciel.nip")
  @Mapping(target = "regon", ignore = true)
  @Mapping(target = "numerKrs", ignore = true)
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWznowienia", source = "dataWznowienia", qualifiedByName = "setDate")

  @Mapping(target = "nazwa", source = "nazwa", qualifiedByName = "setNazwa")
  PodmiotGospodarczeEntity toJdgEntity(@MappingTarget PodmiotGospodarczeEntity entity, JdgSzczegolyDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "wlasciciel", ignore = true)
  @Mapping(target = "adresDzialalnosci", ignore = true)
  @Mapping(target = "adresKorespondencyjny", ignore = true)
  @Mapping(target = "pkdGlowny", ignore = true)
  @Mapping(target = "pkd", ignore = true)
  @Mapping(target = "rejestr", defaultValue = "KRS")
  @Mapping(target = "nip", expression = "java(Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory()) ? odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory().nip() : Strings.EMPTY)")
  @Mapping(target = "regon", expression = "java(Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory()) ? odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory().regon() : Strings.EMPTY)")
  @Mapping(target = "numerKrs", source = "odpis.naglowekA.numerKRS")
  @Mapping(target = "dataRozpoczecia", source = "odpis.naglowekA.dataRejestracjiWKRS", qualifiedByName = "setDateDlaKrs")
  @Mapping(target = "dataZawieszenia", ignore = true)
  @Mapping(target = "dataZakonczenia", ignore = true)
  @Mapping(target = "dataWykreslenia", ignore = true)
  @Mapping(target = "ceidgId", ignore = true)
  @Mapping(target = "link", ignore = true)
  @Mapping(target = "nazwa", source = "odpis.dane.dzial1.danePodmiotu.nazwa")
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "email", expression = "java(Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().siedzibaIAdres()) ? odpisAktualnyResponse.odpis().dane().dzial1().siedzibaIAdres().adresPocztyElektronicznej() : Strings.EMPTY)")
  PodmiotGospodarczeEntity toJdgEntity(OdpisAktualnyResponse odpisAktualnyResponse);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "wlasciciel", ignore = true)
  @Mapping(target = "adresDzialalnosci", ignore = true)
  @Mapping(target = "adresKorespondencyjny", ignore = true)
  @Mapping(target = "pkdGlowny", ignore = true)
  @Mapping(target = "pkd", ignore = true)
  @Mapping(target = "rejestr", defaultValue = "KRS")
  @Mapping(target = "nip", expression = "java(Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory()) ? odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory().nip() : Strings.EMPTY)")
  @Mapping(target = "regon", expression = "java(Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory()) ? odpisAktualnyResponse.odpis().dane().dzial1().danePodmiotu().identyfikatory().regon() : Strings.EMPTY)")
  @Mapping(target = "numerKrs", source = "odpis.naglowekA.numerKRS")
  @Mapping(target = "dataRozpoczecia", source = "odpis.naglowekA.dataRejestracjiWKRS", qualifiedByName = "setDateDlaKrs")
  @Mapping(target = "dataZawieszenia", ignore = true)
  @Mapping(target = "dataZakonczenia", ignore = true)
  @Mapping(target = "dataWykreslenia", ignore = true)
  @Mapping(target = "ceidgId", ignore = true)
  @Mapping(target = "link", ignore = true)
  @Mapping(target = "nazwa", source = "odpis.dane.dzial1.danePodmiotu.nazwa")
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "email", expression = "java(Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().siedzibaIAdres()) ? odpisAktualnyResponse.odpis().dane().dzial1().siedzibaIAdres().adresPocztyElektronicznej() : Strings.EMPTY)")
  PodmiotGospodarczeEntity toJdgEntity(@MappingTarget PodmiotGospodarczeEntity entity, OdpisAktualnyResponse odpisAktualnyResponse);

  @Named("setDate")
  default LocalDate setDate(String date) {
    if (date == null) {
      return null;
    }
    return LocalDate.parse(date);
  }

  @Named("setDateDlaKrs")
  default LocalDate setDateDlaKrs(String date) {
    if (date == null) {
      return null;
    }
    return LocalDate.parse(date, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
  }

  @Named("setNazwa")
  default String setNazwa(String nazwa) {
    return Optional.ofNullable(nazwa)
        .map(n -> {
          String nazwaPodmiotu = n.trim();
          if (nazwaPodmiotu.startsWith("-")) {
            nazwaPodmiotu = nazwaPodmiotu.substring(1);
          }
          return nazwaPodmiotu.trim();
        })
        .orElse(Strings.EMPTY);
  }
}
