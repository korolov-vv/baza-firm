package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.firmycrm.dto.AktualizujSzczegolyKontaktuDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class AktualizujSzczegolyKontaktuUseCase {

    private final FirmyCrmRepository firmyCrmRepository;

    public void zaktualizujInformacjeOKontakcie(UUID firmaKlientUuid, AktualizujSzczegolyKontaktuDto dto) {
        FirmaCrmEntity firmaCrm = firmyCrmRepository
                .findByFirmaKlientUuidAndUuid(firmaKlientUuid, dto.getUuidFirmy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "Brak uprawnień do aktualizacji firmy CRM o UUID: " + dto.getUuidFirmy() + " dla klienta: " + firmaKlientUuid));

        if (firmaCrm.getVersion() != dto.getVersion()) {
            throw new IllegalStateException("Wersja danych jest nieaktualna. Proszę odświeżyć dane i spróbować ponownie.");
        }

        zaktualizujDaneOKontakcie(dto, firmaCrm);

        firmyCrmRepository.save(firmaCrm);
        log.debug("Zaktualizowano szczegóły kontaktu dla firmy CRM: {}, klient: {}",
                dto.getUuidFirmy(), firmaKlientUuid);
    }

    private void zaktualizujDaneOKontakcie(AktualizujSzczegolyKontaktuDto dto, FirmaCrmEntity firmaCrm) {
        firmaCrm.setStatusKontaktu(dto.getStatusKontaktu());
        firmaCrm.setSposobKontaktu(dto.getSposobKontaktu());
        firmaCrm.setDataOstatniegoKontaktu(dto.getDataOstatniegoKontaktu());
        firmaCrm.setDataNastepnegoKontaktu(dto.getDataNastepnegoKontaktu());
        firmaCrm.setKomentarz(dto.getKomentarz());
    }
}

