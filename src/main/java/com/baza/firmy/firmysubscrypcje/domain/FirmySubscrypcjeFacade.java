package com.baza.firmy.firmysubscrypcje.domain;

import com.baza.firmy.request.ZmienStatusSubscrypcjiRequest;
import com.baza.firmy.uzytkownicy.dto.StworzSubscrypcjeDlaFirmyKlientaDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirmySubscrypcjeFacade {

    private final StworzTrialDlaFirmyKlientaUseCase stworzTrialDlaFirmyKlientaUseCase;
    private final StworzSubscrypcjeDlaFirmyKlientaUseCase stworzSubscrypcjeDlaFirmyKlientaUseCase;
    private final ZaktualizujStatusSubscrypcjiFirmyUseCase zaktualizujStatusSubscrypcjiFirmyUseCase;

    @Transactional
    public UUID stworzTrialDlaFirmyKlienta(UUID uuidFirmyKlienta) {
        return stworzTrialDlaFirmyKlientaUseCase.stworzTrialDlaFirmyKlienta(uuidFirmyKlienta);
    }

    @Transactional
    public UUID stworzSubscrypcjeDlaFirmyKlienta(StworzSubscrypcjeDlaFirmyKlientaDto stworzSubscrypcjeDlaFirmyKlientaDto) {
        return stworzSubscrypcjeDlaFirmyKlientaUseCase.stworzSubscrypcjeDlaFirmyKlienta(stworzSubscrypcjeDlaFirmyKlientaDto);
    }

    @Transactional
    public UUID zaktualizujStatusSubscrypcjiFirmy(ZmienStatusSubscrypcjiRequest request) {
        return zaktualizujStatusSubscrypcjiFirmyUseCase.zaktualizujStatusSubscrypcjiFirmy(
            request.uuidSubscrypcji(),
            request.nowyStatus()
        );
    }
}
