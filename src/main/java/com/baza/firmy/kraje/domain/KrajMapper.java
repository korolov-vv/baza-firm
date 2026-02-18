package com.baza.firmy.kraje.domain;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface KrajMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  KrajEntity toKrajEntity(KrajDto dto);

  KrajDto toKrajDto(KrajEntity entity);
}
