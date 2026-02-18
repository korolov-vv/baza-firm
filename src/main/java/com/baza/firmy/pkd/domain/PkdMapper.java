package com.baza.firmy.pkd.domain;

import com.baza.firmy.pkd.domain.dto.PkdDto;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface PkdMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
    PkdEntity toPkdEntity(PkdDto dto);

    @Mapping(target = "nazwa", source = ".", qualifiedByName = "setNazwa")
    PkdDto toPkdDto(PkdEntity entity);

    @Named("setNazwa")
    default String setNazwa(PkdEntity entity) {
        return entity.getNazwa().orElse(Strings.EMPTY);
    }
}
