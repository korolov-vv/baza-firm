package com.baza.firmy.danezportaluzewn.query;

import com.baza.firmy.danezportaluzewn.query.dto.PortalZewnKategoriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface PortalZewnKategoriaMapper {

    PortalZewnKategoriaDto toDto(PortalZewnKategoriaViewEntity entity);
}
