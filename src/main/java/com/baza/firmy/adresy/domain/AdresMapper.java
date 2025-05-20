package com.baza.firmy.adresy.domain;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface AdresMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "kodPocztowy", source = "kod")
  AdresEntity toAdresEntity(AdresDto dto);

  @Mapping(target = "kod", source = "kodPocztowy")
  AdresDto toAdresDto(AdresEntity dto);
}
