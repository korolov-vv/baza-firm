package com.baza.firmy.mapper;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.entity.Adres;
import com.baza.firmy.entity.JednoosobowaDzialalnoscGospodarcza;
import com.baza.firmy.response.AdresDto;
import com.baza.firmy.response.CeidgListDto;
import java.util.List;
import java.util.UUID;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class, BusinessStatus.class })
public interface JednoosobowaDzialalnoscGospodarczaMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "adresKorespondencyjny", ignore = true)
  @Mapping(target = "pkdGlowny", ignore = true)
  @Mapping(target = "pkd", ignore = true)
  @Mapping(target = "spolki", ignore = true)
  @Mapping(target = "dataZawieszenia", ignore = true)
  @Mapping(target = "dataZakonczenia", ignore = true)
  @Mapping(target = "dataWykreslenia", ignore = true)
  @Mapping(target = "dataWznowienia", ignore = true)
  @Mapping(target = "numerStatusu", ignore = true)
  JednoosobowaDzialalnoscGospodarcza toEntity(CeidgListDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "kodPocztowy", source = "kod")
  Adres toAdres(AdresDto dto);

  @IterableMapping(elementTargetType = JednoosobowaDzialalnoscGospodarcza.class)
  List<JednoosobowaDzialalnoscGospodarcza> toEntityList(List<CeidgListDto> dtos);
}
