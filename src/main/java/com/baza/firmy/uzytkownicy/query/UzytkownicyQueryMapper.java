package com.baza.firmy.uzytkownicy.query;

import com.baza.firmy.uzytkownicy.dto.UzytkownikDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
public interface UzytkownicyQueryMapper {

  @Mapping(target = "podmiotGospodarczyUuid", source = "firma.uuid")
  @Mapping(target = "nip", source = "firma.nip")
  UzytkownikDto toUzytkownikDto(UzytkownikViewEntity entity);
}
