package com.baza.firmy.pkd.domain;

import com.baza.firmy.pkd.domain.dto.PkdDto;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface PkdMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  PkdEntity toPkdEntity(PkdDto dto);

  PkdDto toPkdDto(PkdEntity entity);
}
