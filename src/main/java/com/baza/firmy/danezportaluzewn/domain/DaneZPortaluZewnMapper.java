package com.baza.firmy.danezportaluzewn.domain;

import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DaneZPortaluZewnMapper {

    PortalZewnEntity toEntity(DaneZPortaluZewnDto dto);
}
