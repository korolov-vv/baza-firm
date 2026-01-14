package com.baza.firmy.firmycrm.query;

import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class })
interface FirmyCrmQueryMapper {

    @Mapping(target = "pkdGlowny", source = ".", qualifiedByName = "setPkdGlowny")
    @Mapping(target = "nip", source = ".", qualifiedByName = "setNip")
    @Mapping(target = "nazwa", source = "firmaCrm.nazwa")
    @Mapping(target = "wojewodztwo", source = "firmaCrm.adresKorespondencyjny.wojewodztwo")
    @Mapping(target = "telefon", source = "firmaCrm.telefon")
    @Mapping(target = "email", source = "firmaCrm.email")
    FirmaCrmListDto toFirmaCrmListDto(FirmaCrmViewEntity entity);

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
}
