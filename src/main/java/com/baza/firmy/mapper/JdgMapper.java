package com.baza.firmy.mapper;

import com.baza.firmy.dto.WlascicielDto;
import com.baza.firmy.entity.Adres;
import com.baza.firmy.entity.Jdg;
import com.baza.firmy.entity.Kraj;
import com.baza.firmy.entity.Osoba;
import com.baza.firmy.entity.Pkd;
import com.baza.firmy.response.AdresDto;
import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.response.JdgSzczegolyDto;
import com.baza.firmy.response.KrajDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = { UUID.class, LocalDateTime.class })
public interface JdgMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
  @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkd")
  @Mapping(target = "wlasciciel", source = ".", qualifiedByName = "setWlasciciel")
  Jdg toJdgEntity(JdgSzczegolyDto dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
  @Mapping(target = "pelneInfo", source = ".")
  @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
  @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkd")
  @Mapping(target = "wlasciciel", source = ".", qualifiedByName = "setWlasciciel")
  @Mapping(target = "dataRozpoczecia", source = "dataRozpoczecia", qualifiedByName = "setDate")
  @Mapping(target = "dataZawieszenia", source = "dataZawieszenia", qualifiedByName = "setDate")
  @Mapping(target = "dataZakonczenia", source = "dataZakonczenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWykreslenia", source = "dataWykreslenia", qualifiedByName = "setDate")
  @Mapping(target = "dataWznowienia", source = "dataWznowienia", qualifiedByName = "setDate")
  Jdg toJdgEntity(@MappingTarget Jdg entity, JdgSzczegolyDto dto);

  @Mapping(target = "pkdGlowny", source = "pkdGlowny.kod")
  @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkdString")
  JdgListDto toJdgListDtoList(Jdg entity);

  List<JdgListDto> toJdgListDtoList(List<Jdg> entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "kodPocztowy", source = "kod")
  Adres toAdresEntity(AdresDto dto);

  @Mapping(target = "kod", source = "kodPocztowy")
  AdresDto toAdresDto(Adres dto);

  WlascicielDto toWlascicielDto(Osoba entity);

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

  @Named("setPkdString")
  default List<String> setPkdString(Jdg entity) {
    return entity.getPkd().stream()
        .map(Pkd::getKod)
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

  @Named("setDate")
  default LocalDate setDate(String date) {
    return LocalDate.parse(date);
  }
}
