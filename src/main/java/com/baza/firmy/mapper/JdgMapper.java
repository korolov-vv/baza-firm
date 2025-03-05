package com.baza.firmy.mapper;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.entity.Adres;
import com.baza.firmy.entity.Jdg;
import com.baza.firmy.entity.Kraj;
import com.baza.firmy.entity.Osoba;
import com.baza.firmy.entity.Pkd;
import com.baza.firmy.response.AdresDto;
import com.baza.firmy.response.JdgSzczegolyDto;
import com.baza.firmy.response.KrajDto;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = { UUID.class, BusinessStatus.class, Pkd.class })
public interface JdgMapper {

  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
  @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkd")
  @Mapping(target = "wlasciciel", source = ".", qualifiedByName = "setWlasciciel")
  Jdg toJdgEntity(JdgSzczegolyDto dto);

  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
  @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkd")
  @Mapping(target = "wlasciciel", source = ".", qualifiedByName = "setWlasciciel")
  Jdg toJdgEntity(@MappingTarget Jdg entity, JdgSzczegolyDto dto);

  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "kodPocztowy", source = "kod")
  Adres toAdresEntity(AdresDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  Kraj toKrajEntity(KrajDto dto);

  @Named("setPkdGlowny")
  default Pkd setPkdGlowny(JdgSzczegolyDto dto) {

    Pkd pkd = new Pkd();
    pkd.setUuid(UUID.randomUUID());
    pkd.setKod(dto.getPkdGlowny());
    return pkd;
  }

  @Named("setPkd")
  default List<Pkd> setPkd(JdgSzczegolyDto dto) {
    return dto.getPkd().stream()
        .map(pkdDto -> {
          Pkd pkd = new Pkd();
          pkd.setUuid(UUID.randomUUID());
          pkd.setKod(pkdDto);
          return pkd;
        })
        .toList();
  }

  @Named("setWlasciciel")
  default Osoba setWlasciciel(JdgSzczegolyDto dto) {
    Osoba osoba = new Osoba();
    osoba.setUuid(UUID.randomUUID());
    osoba.setImie(dto.getWlasciciel().getImie());
    osoba.setNazwisko(dto.getWlasciciel().getNazwisko());
    osoba.setNip(dto.getWlasciciel().getNip());
    osoba.setRegon(dto.getWlasciciel().getRegon());
    osoba.getObywatelstwa().addAll(dto.getObywatelstwa().stream().map(this::toKrajEntity).toList());
    return osoba;
  }
}
