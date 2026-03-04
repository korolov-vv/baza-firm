package com.baza.firmy.firmycrm.query;

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
interface FirmyCrmQueryMapper {

    @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
    @Mapping(target = "nip", source = ".", qualifiedByName = "setNip")
    @Mapping(target = "nazwa", source = "firmaCrm.nazwa")
    @Mapping(target = "wojewodztwo", source = ".", qualifiedByName = "setWojewodztwo")
    @Mapping(target = "telefon", source = "firmaCrm.telefon")
    @Mapping(target = "email", source = "firmaCrm.email")
    @Mapping(target = "statusKontaktu", source = "statusKontaktu.label")
    FirmaCrmListDto toFirmaCrmListDto(FirmaCrmViewEntity entity);

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
    FirmaCrmDto toFirmaCrmDto(FirmaCrmViewEntity entity);

    @Named("setPkdGlowny")
    default Pkd setPkdGlowny(FirmaCrmViewEntity entity) {
        return entity.getFirmaCrm().getPkdGlowny()
                .map(pkd -> Pkd.builder()
                        .kod(pkd.getKod())
                        .nazwa(pkd.getNazwa().orElse(Strings.EMPTY))
                        .build())
                .orElse(null);
    }

    @Named("setNip")
    default String setNip(FirmaCrmViewEntity firmaCrmViewEntity) {
        return firmaCrmViewEntity.getFirmaCrm().getNip();
    }

    @Named("setPkdGlownyString")
    default String setPkdGlownyString(FirmaCrmViewEntity entity) {
        return entity.getFirmaCrm().getPkdGlowny()
                .map(pkd -> pkd.getKod() + pkd.getNazwa()
                        .map(nazwa -> " - " + nazwa)
                        .orElse(Strings.EMPTY)
                )
                .orElse(null);
    }

    @Named("setPozostalePkd")
    default String setPozostalePkd(FirmaCrmViewEntity entity) {
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
    default String setAdresKorespondencyjny(FirmaCrmViewEntity entity) {
        return entity.getFirmaCrm().getAdresKorespondencyjny()
                .map(AdresViewEntity::toString)
                .orElse(null);
    }

    @Named("setWojewodztwo")
    default String setWojewodztwo(FirmaCrmViewEntity entity) {
        return entity.getFirmaCrm().getAdresKorespondencyjny()
                .map(AdresViewEntity::getWojewodztwo)
                .orElse(null);
    }

    @Named("setAdresDzialalnosci")
    default String setAdresDzialalnosci(FirmaCrmViewEntity entity) {
        return entity.getFirmaCrm().getAdresDzialalnosci()
                .map(AdresViewEntity::toString)
                .orElse(null);
    }
}

