package com.baza.firmy.osoby.domain;

import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import com.baza.firmy.osoby.domain.dto.WlascicielDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface OsobaMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "pesel", ignore = true)
  @Mapping(target = "obywatelstwa", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  OsobaEntity toOsobaEntity(StworzWlascicielaDto dto);

  WlascicielDto toWlascicielDto(OsobaEntity entity);
}
