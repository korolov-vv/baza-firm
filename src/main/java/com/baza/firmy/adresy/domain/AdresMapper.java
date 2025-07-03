package com.baza.firmy.adresy.domain;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.response.krs.SiedzibaIAdresResponse;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface AdresMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "kodPocztowy", source = "kod")
  AdresEntity toAdresEntity(AdresDto dto);

  @Mapping(target = "kod", source = "kodPocztowy")
  AdresDto toAdresDto(AdresEntity dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "miasto", source = "miejscowosc")
  @Mapping(target = "budynek", source = "nrDomu")
  @Mapping(target = "lokal", source = "nrLokalu")
  AdresEntity toAdresEntity(SiedzibaIAdresResponse.AdresKrsResponse adresKrsResponse);
}
