package com.baza.firmy.firmycrm.query;

import com.baza.firmy.firmycrm.dto.FirmaCrmDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirmyCrmQueryFacade {

    private final FirmyCrmQueryRepository firmyCrmQueryRepository;
    private final FirmyCrmQueryMapper firmyCrmQueryMapper;

    public Page<FirmaCrmListDto> pobierzListeFirm(Specification<FirmaCrmViewEntity> specification, Pageable pageable) {
        return firmyCrmQueryRepository.findAll(specification, pageable)
                .map(firmyCrmQueryMapper::toFirmaCrmListDto);
    }

    public Optional<FirmaCrmDto> pobierzSzczegolyFirmy(UUID firmaKlientUuid, UUID firmaCrmUuid) {
        return firmyCrmQueryRepository.findByFirmaKlientUuidAndUuid(firmaKlientUuid, firmaCrmUuid)
                .map(firmyCrmQueryMapper::toFirmaCrmDto);
    }
}
