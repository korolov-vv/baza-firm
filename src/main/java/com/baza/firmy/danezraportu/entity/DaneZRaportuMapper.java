package com.baza.firmy.danezraportu.entity;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface DaneZRaportuMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "version", source = "version", defaultValue = "1")
  DaneZRaportuEntity toEntity(DaneZRaportuDto daneZRaportuDto);

  DaneZRaportuDto toDto(DaneZRaportuEntity daneZRaportuEntity);
}
