package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.query.SubscrypcjaViewEntity;
import com.baza.firmy.subscrypcje.query.SubscrypcjeQueryFacade;
import com.baza.firmy.uzytkownicy.dto.StworzSubscrypcjeDlaFirmyKlientaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzTrialDlaFirmyKlientaUseCase {

  public static final String TRIAL = "TRIAL";

  private final StworzSubscrypcjeDlaFirmyKlientaUseCase stworzSubscrypcjeDlaFirmyKlientaUseCase;
  private final SubscrypcjeQueryFacade subscrypcjeQueryFacade;

  public UUID stworzTrialDlaFirmyKlienta(UUID uuidFirmyKlienta) {
    SubscrypcjaViewEntity subscrypcja = subscrypcjeQueryFacade.findByNazwa(TRIAL)
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja o nazwie: " + TRIAL + " nie istnieje"));

    return stworzSubscrypcjeDlaFirmyKlientaUseCase.stworzSubscrypcjeDlaFirmyKlienta(new StworzSubscrypcjeDlaFirmyKlientaDto(uuidFirmyKlienta, subscrypcja.getUuid(), StatusSubscrypcji.AKTYWNA));
  }
}
