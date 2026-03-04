package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.adresy.query.AdresViewEntity;
import com.baza.firmy.firmycrm.dto.FirmaCrmDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface FirmyCrmMapper {

    @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
    @Mapping(target = "nip", source = ".", qualifiedByName = "setNip")
    @Mapping(target = "nazwa", source = "firmaCrm.nazwa")
    @Mapping(target = "wojewodztwo", source = "firmaCrm.adresKorespondencyjny.wojewodztwo")
    @Mapping(target = "telefon", source = "firmaCrm.telefon")
    @Mapping(target = "email", source = "firmaCrm.email")
    @Mapping(target = "statusKontaktu", source = "statusKontaktu.label")
    FirmaCrmListDto toFirmaCrmListDto(FirmaCrmEntity entity);

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "nazwa", source = "firmaCrm.nazwa")
    @Mapping(target = "nip", source = "firmaCrm.nip")
    @Mapping(target = "regon", source = "firmaCrm.regon")
    @Mapping(target = "adresKorespondencyjny", source = ".", qualifiedByName = "setAdresKorespondencyjny")
    @Mapping(target = "adresDzialalnosci", source = ".", qualifiedByName = "setAdresDzialalnosci")
    @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlownyString")
    @Mapping(target = "pozostalePkd", source = ".", qualifiedByName = "setPozostalePkd")
    @Mapping(target = "telefon", source = "firmaCrm.telefon")
    @Mapping(target = "email", source = "firmaCrm.email")
    @Mapping(target = "stronaWww", source = "firmaCrm.www")
    FirmaCrmDto toFirmaCrmDto(FirmaCrmEntity entity);

    @Named("setPkdGlowny")
    default Pkd setPkdGlowny(FirmaCrmEntity entity) {
        return entity.getFirmaCrm().getPkdGlowny()
                .map(pkd -> Pkd.builder()
                        .kod(pkd.getKod())
                        .nazwa(pkd.getNazwa().orElse(Strings.EMPTY))
                        .build())
                .orElse(null);
    }

    @Named("setNip")
    default String setNip(FirmaCrmEntity firmaCrmViewEntity) {
        return firmaCrmViewEntity.getFirmaCrm().getNip();
    }

    @Named("setPkdGlownyString")
    default String setPkdGlownyString(FirmaCrmEntity entity) {
        return entity.getFirmaCrm().getPkdGlowny()
                .map(pkd -> pkd.getKod() + pkd.getNazwa()
                        .map(nazwa -> " - " + nazwa)
                        .orElse(Strings.EMPTY)
                )
                .orElse(null);
    }

    @Named("setPozostalePkd")
    default String setPozostalePkd(FirmaCrmEntity entity) {
        return entity.getFirmaCrm().getPkd().stream()
                .filter(pkd -> entity.getFirmaCrm().getPkdGlowny()
                        .map(glowny -> !glowny.getKod().equals(pkd.getKod()))
                        .orElse(true))
                .map(pkd -> pkd.getKod() + pkd.getNazwa()
                        .map(nazwa -> " - " + nazwa)
                        .orElse(Strings.EMPTY)
                )
                .collect(Collectors.joining("; "));
    }

    @Named("setAdresKorespondencyjny")
    default String setAdresKorespondencyjny(FirmaCrmEntity entity) {
        return entity.getFirmaCrm().getAdresKorespondencyjny()
                .map(AdresViewEntity::toString)
                .orElse(null);
    }

    @Named("setAdresDzialalnosci")
    default String setAdresDzialalnosci(FirmaCrmEntity entity) {
        return entity.getFirmaCrm().getAdresDzialalnosci()
                .map(AdresViewEntity::toString)
                .orElse(null);
    }
}

