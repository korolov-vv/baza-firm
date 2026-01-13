package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirmyCrmFacade {

    private final StworzFirmaCrmUseCase stworzFirmaCrmUseCase;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List<UUID> stworzFirmyCrm(PodmiotGospodarczyViewEntity firmaKlient, List<PodmiotGospodarczyViewEntity> firmyCrm) {
        return firmyCrm.stream()
                .map(firma -> stworzFirmaCrmUseCase.stworzFirmaCrm(firmaKlient, firma))
                .filter(Objects::nonNull)
                .toList();
    }
}
