package com.baza.firmy.podmiotygospodarcze.query;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.osoby.domain.dto.WlascicielDto;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.query.PkdViewEntity;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface PodmiotyGospodarczeQueryMapper {

  @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlownyString")
  @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkdStringList")
  @Mapping(target = "nip", source = ".", qualifiedByName = "setNip")
  @Mapping(target = "regon", source = ".", qualifiedByName = "setRegon")
  @Mapping(target = "krs", source = ".", qualifiedByName = "setKrs")
  @Mapping(target = "adresDzialalnosci", source = ".", qualifiedByName = "setAdresEntityDzialalnosci")
  @Mapping(target = "adresKorespondencyjny", source = ".", qualifiedByName = "setAdresKorespondencyjny")
  JdgListDto toJdgListDtoList(PodmiotGospodarczeViewEntity entity);

  @Named("setPkdGlownyString")
  default String setPkdGlownyString(PodmiotGospodarczeViewEntity entity) {
    return entity.getPkdGlowny().map(PkdViewEntity::getKod).orElse(Strings.EMPTY);
  }

  @Named("setPkdStringList")
  default List<String> setPkdStringList(PodmiotGospodarczeViewEntity entity) {
    return entity.getPkd().stream()
        .map(PkdViewEntity::getKod)
        .toList();
  }

  @Named("setNip")
  default String setNip(PodmiotGospodarczeViewEntity jdg) {
    return jdg.getWlasciciel().map(OsobaViewEntity::getNip).orElse(Strings.EMPTY);
  }

  @Named("setRegon")
  default String setRegon(PodmiotGospodarczeViewEntity jdg) {
    return jdg.getWlasciciel().map(OsobaViewEntity::getRegon).orElse(Strings.EMPTY);
  }

  @Named("setKrs")
  default String setKrs(PodmiotGospodarczeViewEntity jdg) {
    return jdg.getNumerKrs().orElse(Strings.EMPTY);
  }

  @Named("setAdresEntityDzialalnosci")
  default AdresDto setAdresEntityDzialalnosci(PodmiotGospodarczeViewEntity jdg) {
    return jdg.getAdresDzialalnosci()
        .map(adres -> AdresDto.builder()
            .ulica(adres.getUlica())
            .budynek(adres.getBudynek())
            .lokal(adres.getLokal())
            .miasto(adres.getMiasto())
            .wojewodztwo(adres.getWojewodztwo())
            .powiat(adres.getPowiat())
            .gmina(adres.getGmina())
            .kraj(adres.getKraj())
            .kod(adres.getKodPocztowy())
            .build())
        .orElse(null);
  }

  @Named("setAdresKorespondencyjny")
  default AdresDto setAdresKorespondencyjny(PodmiotGospodarczeViewEntity jdg) {
    return AdresDto.builder()
            .ulica(jdg.getAdresKorespondencyjny().getUlica())
            .budynek(jdg.getAdresKorespondencyjny().getBudynek())
            .lokal(jdg.getAdresKorespondencyjny().getLokal())
            .miasto(jdg.getAdresKorespondencyjny().getMiasto())
            .wojewodztwo(jdg.getAdresKorespondencyjny().getWojewodztwo())
            .powiat(jdg.getAdresKorespondencyjny().getPowiat())
            .gmina(jdg.getAdresKorespondencyjny().getGmina())
            .kraj(jdg.getAdresKorespondencyjny().getKraj())
            .kod(jdg.getAdresKorespondencyjny().getKodPocztowy())
            .build();
  }

  @Named("setWlascicielDto")
  default WlascicielDto setWlascicielDto(PodmiotGospodarczeViewEntity jdg) {
    return jdg.getWlasciciel()
        .map(osoba -> WlascicielDto.builder()
            .imie(osoba.getImie())
            .nazwisko(osoba.getNazwisko())
            .nip(osoba.getNip())
            .regon(osoba.getRegon())
            .build())
        .orElse(null);
  }
}
