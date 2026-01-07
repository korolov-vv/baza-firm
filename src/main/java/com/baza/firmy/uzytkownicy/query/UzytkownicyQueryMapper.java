package com.baza.firmy.uzytkownicy.query;

import com.baza.firmy.uzytkownicy.domain.dto.UzytkownikDto;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface UzytkownicyQueryMapper {

  UzytkownikDto toUzytkownikDto(UzytkownikViewEntity entity);
}
