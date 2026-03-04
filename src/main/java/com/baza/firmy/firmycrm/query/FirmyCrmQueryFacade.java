package com.baza.firmy.firmycrm.query;

import com.baza.firmy.firmycrm.dto.FirmaCrmDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmFiltryDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirmyCrmQueryFacade {

    private final FirmyCrmQueryRepository firmyCrmQueryRepository;
    private final FirmyCrmQueryMapper firmyCrmQueryMapper;

    public Page<FirmaCrmListDto> pobierzListeFirm(FirmaCrmFiltryDto filtry, Pageable pageable) {
        Specification<FirmaCrmViewEntity> specification = createSpecification(filtry);

        return firmyCrmQueryRepository.findAll(specification, pageable)
                .map(firmyCrmQueryMapper::toFirmaCrmListDto);
    }

    public Page<FirmaCrmListDto> pobierzListeFirmDoKontaktuDzis(FirmaCrmFiltryDto filtry, Pageable pageable) {

        return firmyCrmQueryRepository.znajdzFirmyDoKontaktu(
                filtry.getUuidFirmyKlienta().orElseThrow(() -> new IllegalArgumentException("UUID firmy klienta jest wymagany")),
                        LocalDate.now().atStartOfDay(),
                        pageable
                )
                .map(firmyCrmQueryMapper::toFirmaCrmListDto);
    }

    public int pobierzIloscFirmDoKontaktuDzis(FirmaCrmFiltryDto filtry) {

        return firmyCrmQueryRepository.pobierzIloscFirmDoKontaktuDzis(
                filtry.getUuidFirmyKlienta().orElseThrow(() -> new IllegalArgumentException("UUID firmy klienta jest wymagany")),
                LocalDate.now().atStartOfDay()
                );
    }

    public Optional<FirmaCrmDto> pobierzSzczegolyFirmy(UUID firmaKlientUuid, UUID firmaCrmUuid) {
        return firmyCrmQueryRepository.findByFirmaKlientUuidAndUuid(firmaKlientUuid, firmaCrmUuid)
                .map(firmyCrmQueryMapper::toFirmaCrmDto);
    }

    private Specification<FirmaCrmViewEntity> createSpecification(FirmaCrmFiltryDto filtry) {
        SpecificationBuilder<FirmyCrmFilterSpecification> builder = SpecificationBuilder.specification(
                FirmyCrmFilterSpecification.class);

        if (filtry != null) {
            filtry.getPkd().ifPresent(pkd -> builder.withParam("pkd", pkd));

            filtry.getDataRozpoczeciaOd().ifPresent(data ->
                    builder.withParam("dataRozpoczeciaOd", data.format(DateTimeFormatter.ISO_DATE)));

            filtry.getDataRozpoczeciaDo().ifPresent(data ->
                    builder.withParam("dataRozpoczeciaDo", data.format(DateTimeFormatter.ISO_DATE)));

            filtry.getDataOstatniegoKontaktuDo().ifPresent(data ->
                    builder.withParam("dataOstatniegoKontaktuDo", data.format(DateTimeFormatter.ISO_DATE)));

            filtry.getDataNastepnegoKontaktuOd().ifPresent(data ->
                    builder.withParam("dataNastepnegoKontaktuOd", data.format(DateTimeFormatter.ISO_DATE)));

            filtry.getDataDodaniaDoBazy().ifPresent(data ->
                    builder.withParam("dataDodaniaDoBazy", data.format(DateTimeFormatter.ISO_DATE)));

            filtry.getWojewodztwo().ifPresent(woj -> builder.withParam("wojewodztwo", woj));

            filtry.getPowiat().ifPresent(pow -> builder.withParam("powiat", pow));

            filtry.getGmina().ifPresent(gm -> builder.withParam("gmina", gm));

            filtry.getUuidFirmyKlienta().ifPresent(uuid -> builder.withParam("uuidFirmyKlienta", uuid.toString()));
        }

        return builder.build();
    }
}
