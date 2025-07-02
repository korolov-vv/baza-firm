package com.baza.firmy.danezkrs.domain;

import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface ListaZaktualizowanychWpisowKrsMapper {

  @Mapping (target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "czyObsluzona", ignore = true)
  @Mapping(target = "nieobsluzoneKrsy", ignore = true)
  ListaZaktualizowanychWpisowKrsEntity toEntity(ListaZmienionychWpisowKrsResponse dto);
}
