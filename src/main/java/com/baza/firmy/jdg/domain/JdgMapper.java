package com.baza.firmy.jdg.domain;

import com.baza.firmy.jdg.domain.dto.JdgSzczegolyDto;
import java.time.LocalDate;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface JdgMapper {

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
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWznowienia", source = "dataWznowienia", qualifiedByName = "setDate")
  JdgEntity toJdgEntity(JdgSzczegolyDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "wlasciciel", ignore = true)
  @Mapping(target = "adresDzialalnosci", ignore = true)
  @Mapping(target = "adresKorespondencyjny", ignore = true)
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWznowienia", source = "dataWznowienia", qualifiedByName = "setDate")
  @Mapping(target = "pkdGlowny", ignore = true)
  @Mapping(target = "pkd", ignore = true)
  JdgEntity toJdgEntity(@MappingTarget JdgEntity entity, JdgSzczegolyDto dto);

  @Named("setDate")
  default LocalDate setDate(String date) {
    if (date == null) {
      return null;
    }
    return LocalDate.parse(date);
  }
}
