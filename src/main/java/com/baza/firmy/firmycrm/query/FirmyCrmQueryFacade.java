package com.baza.firmy.firmycrm.query;

import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
}
