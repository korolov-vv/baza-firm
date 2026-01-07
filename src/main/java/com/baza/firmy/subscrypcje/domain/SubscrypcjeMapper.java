package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.domain.dto.StworzSubscrypcjeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface SubscrypcjeMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  SubscrypcjaEntity toSubscrypcjaEntity(StworzSubscrypcjeDto dto);
}
