package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.uzytkownicy.dto.StworzUzytkownikaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface UzytkownicyMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", source = ".", qualifiedByName = "setUuid")
  UzytkownikEntity toUzytkownikEntity(StworzUzytkownikaDto dto);

  @Named("setUuid")
  default UUID setUuid(StworzUzytkownikaDto dto) {
    return dto.getKeycloakUuid() != null ? dto.getKeycloakUuid() : UUID.randomUUID();
  }
}
