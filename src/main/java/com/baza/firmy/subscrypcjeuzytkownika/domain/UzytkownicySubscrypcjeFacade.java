package com.baza.firmy.subscrypcjeuzytkownika.domain;

import com.baza.firmy.uzytkownicy.dto.StworzSubscrypcjeDlaFirmyKlientaDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UzytkownicySubscrypcjeFacade {

    private final StworzTrialDlaFirmyKlientaUseCase stworzTrialDlaFirmyKlientaUseCase;
    private final StworzSubscrypcjeDlaFirmyKlientaUseCase stworzSubscrypcjeDlaFirmyKlientaUseCase;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public UUID stworzTrialDlaFirmyKlienta(UUID uuidFirmyKlienta) {
        return stworzTrialDlaFirmyKlientaUseCase.stworzTrialDlaFirmyKlienta(uuidFirmyKlienta);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public UUID stworzSubscrypcjeDlaFirmyKlienta(StworzSubscrypcjeDlaFirmyKlientaDto stworzSubscrypcjeDlaFirmyKlientaDto) {
        return stworzSubscrypcjeDlaFirmyKlientaUseCase.stworzSubscrypcjeDlaFirmyKlienta(stworzSubscrypcjeDlaFirmyKlientaDto);
    }
}
