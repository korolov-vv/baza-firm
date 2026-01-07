package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.uzytkownicy.domain.dto.StworzUzytkownikaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface UzytkownicyMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  UzytkownikEntity toUzytkownikEntity(StworzUzytkownikaDto dto);
}
