package com.baza.firmy.mapper;

import com.baza.firmy.entity.ListaJdgPobieranie;
import com.baza.firmy.response.ListaJdgDto;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class, LocalDateTime.class })
public interface ListaJdgPobieranieMapper {

  @Mapping (target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
  @Mapping(target = "next", source = "links.next")
  @Mapping(target = "prev", source = "links.prev")
  @Mapping(target = "self", source = "links.self")
  @Mapping(target = "first", source = "links.first")
  @Mapping(target = "last", source = "links.last")
  @Mapping(target = "czyObsluzona", ignore = true)
  @Mapping(target = "nieobsluzoneLinki", ignore = true)
  ListaJdgPobieranie toEntity(ListaJdgDto dto);
}
