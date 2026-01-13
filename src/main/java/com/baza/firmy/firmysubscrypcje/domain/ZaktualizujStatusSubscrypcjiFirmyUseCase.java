package com.baza.firmy.firmysubscrypcje.domain;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class ZaktualizujStatusSubscrypcjiFirmyUseCase {

  private final FirmySubscrypcjeRepository firmySubscrypcjeRepository;

  public UUID zaktualizujStatusSubscrypcjiFirmy(UUID uuid, StatusSubscrypcji nowyStatus) {
    FirmaSubscrypcjaEntity firmaSubscrypcja = firmySubscrypcjeRepository.findByUuid(uuid)
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja firmy o UUID: " + uuid + " nie istnieje"));

    firmaSubscrypcja.setStatusSubscrypcji(nowyStatus);

    return firmySubscrypcjeRepository.save(firmaSubscrypcja).getUuid();
  }
}

