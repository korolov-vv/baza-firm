package com.baza.firmy.podmiotygospodarcze.query;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.dto.PodmiotGospodarczyListDto;
import com.baza.firmy.osoby.domain.dto.WlascicielDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface PodmiotyGospodarczeQueryMapper {

    @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
    @Mapping(target = "pkd", source = ".", qualifiedByName = "setPkdList")
    @Mapping(target = "nip", source = ".", qualifiedByName = "setNip")
    @Mapping(target = "regon", source = ".", qualifiedByName = "setRegon")
    @Mapping(target = "krs", source = ".", qualifiedByName = "setKrs")
    @Mapping(target = "adresDzialalnosci", source = ".", qualifiedByName = "setAdresEntityDzialalnosci")
    @Mapping(target = "adresKorespondencyjny", source = ".", qualifiedByName = "setAdresKorespondencyjny")
    PodmiotGospodarczyListDto toJdgListDtoList(PodmiotGospodarczeViewEntity entity);

    @Named("setPkdGlowny")
    default Pkd setPkdGlowny(PodmiotGospodarczeViewEntity entity) {
        return entity.getPkdGlowny()
                .map(pkd -> Pkd.builder()
                        .kod(pkd.getKod())
                        .nazwa(pkd.getNazwa().orElse(Strings.EMPTY))
                        .build())
                .orElse(null);
    }

    @Named("setPkdList")
    default List<Pkd> setPkdList(PodmiotGospodarczeViewEntity entity) {
        return entity.getPkd().stream()
                .map(pkd -> Pkd.builder()
                        .kod(pkd.getKod())
                        .nazwa(pkd.getNazwa().orElse(Strings.EMPTY))
                        .build())
                .toList();
    }

    @Named("setNip")
    default String setNip(PodmiotGospodarczeViewEntity podmiotGospodarczy) {
        return podmiotGospodarczy.getRegon();
    }


    @Named("setRegon")
    default String setRegon(PodmiotGospodarczeViewEntity podmiotGospodarczy) {
        return podmiotGospodarczy.getNip();
    }

    @Named("setKrs")
    default String setKrs(PodmiotGospodarczeViewEntity podmiotGospodarczy) {
        return podmiotGospodarczy.getNumerKrs().orElse(Strings.EMPTY);
    }

    @Named("setAdresEntityDzialalnosci")
    default AdresDto setAdresEntityDzialalnosci(PodmiotGospodarczeViewEntity podmiotGospodarczy) {
        return podmiotGospodarczy.getAdresDzialalnosci()
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
    default AdresDto setAdresKorespondencyjny(PodmiotGospodarczeViewEntity podmiotGospodarczy) {
        return AdresDto.builder()
                .ulica(podmiotGospodarczy.getAdresKorespondencyjny().getUlica())
                .budynek(podmiotGospodarczy.getAdresKorespondencyjny().getBudynek())
                .lokal(podmiotGospodarczy.getAdresKorespondencyjny().getLokal())
                .miasto(podmiotGospodarczy.getAdresKorespondencyjny().getMiasto())
                .wojewodztwo(podmiotGospodarczy.getAdresKorespondencyjny().getWojewodztwo())
                .powiat(podmiotGospodarczy.getAdresKorespondencyjny().getPowiat())
                .gmina(podmiotGospodarczy.getAdresKorespondencyjny().getGmina())
                .kraj(podmiotGospodarczy.getAdresKorespondencyjny().getKraj())
                .kod(podmiotGospodarczy.getAdresKorespondencyjny().getKodPocztowy())
                .build();
    }

    @Named("setWlascicielDto")
    default WlascicielDto setWlascicielDto(PodmiotGospodarczeViewEntity podmiotGospodarczy) {
        return podmiotGospodarczy.getWlasciciel()
                .map(osoba -> WlascicielDto.builder()
                        .imie(osoba.getImie())
                        .nazwisko(osoba.getNazwisko())
                        .nip(osoba.getNip())
                        .regon(osoba.getRegon())
                        .build())
                .orElse(null);
    }
}
