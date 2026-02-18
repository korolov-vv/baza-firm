package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.firmycrm.dto.AktualizujSzczegolyKontaktuDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmDto;
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
    private final AktualizujSzczegolyKontaktuUseCase aktualizujSzczegolyKontaktuUseCase;

    @Transactional
    public List<UUID> stworzFirmyCrm(UUID firmaKlientUuid, List<UUID> firmyCrmUuids) {
        return firmyCrmUuids.stream()
                .map(uuid -> stworzFirmaCrmUseCase.stworzFirmaCrm(firmaKlientUuid, uuid))
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional
    public FirmaCrmDto aktualizujSzczegolyKontaktu(UUID firmaKlientUuid, AktualizujSzczegolyKontaktuDto dto) {
        return aktualizujSzczegolyKontaktuUseCase.zaktualizujInformacjeOKontakcie(firmaKlientUuid, dto);
    }


}
