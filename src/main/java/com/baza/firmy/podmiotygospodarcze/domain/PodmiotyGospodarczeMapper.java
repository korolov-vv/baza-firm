package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = { UUID.class, Rejestr.class })
interface PodmiotyGospodarczeMapper {

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
  @Mapping(target = "rejestr", defaultValue = "CEIDG")
  @Mapping(target = "nip", source = "dto.wlasciciel.nip")
  @Mapping(target = "regon", ignore = true)
  @Mapping(target = "numerKrs", ignore = true)
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "nazwa", source = "nazwa", qualifiedByName = "setNazwa")
  PodmiotyGospodarczeEntity toJdgEntity(JdgSzczegolyDto dto);

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
  @Mapping(target = "rejestr", defaultValue = "CEIDG")
  @Mapping(target = "nip", source = "dto.wlasciciel.nip")
  @Mapping(target = "regon", ignore = true)
  @Mapping(target = "numerKrs", ignore = true)
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWznowienia", source = "dataWznowienia", qualifiedByName = "setDate")

  @Mapping(target = "nazwa", source = "nazwa", qualifiedByName = "setNazwa")
  PodmiotyGospodarczeEntity toJdgEntity(@MappingTarget PodmiotyGospodarczeEntity entity, JdgSzczegolyDto dto);

  @Named("setDate")
  default LocalDate setDate(String date) {
    if (date == null) {
      return null;
    }
    return LocalDate.parse(date);
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
