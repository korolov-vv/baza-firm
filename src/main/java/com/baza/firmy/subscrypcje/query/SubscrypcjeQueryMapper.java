package com.baza.firmy.subscrypcje.query;

import com.baza.firmy.subscrypcje.dto.SubscrypcjaDto;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface SubscrypcjeQueryMapper {

  SubscrypcjaDto toSubscrypcjaDto(SubscrypcjaViewEntity entity);
}
