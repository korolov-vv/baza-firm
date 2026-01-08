package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.query.SubscrypcjaViewEntity;
import com.baza.firmy.subscrypcje.query.SubscrypcjeQueryFacade;
import com.baza.firmy.uzytkownicy.dto.StworzSubscrypcjeUzytkownikaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzTrialUzytkownikaUseCase {

  public static final String TRIAL = "TRIAL";

  private final StworzSubscrypcjeUzytkownikaUseCase stworzSubscrypcjeUzytkownikaUseCase;
  private final SubscrypcjeQueryFacade subscrypcjeQueryFacade;

  public UUID stworzTrialUzytkownika(UUID uzytkownikUuid) {
    SubscrypcjaViewEntity subscrypcja = subscrypcjeQueryFacade.findByNazwa(TRIAL)
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja o nazwie: " + TRIAL + " nie istnieje"));

    return stworzSubscrypcjeUzytkownikaUseCase.stworzSubscrypcjeUzytkownika(new StworzSubscrypcjeUzytkownikaDto(uzytkownikUuid, subscrypcja.getUuid(), StatusSubscrypcji.AKTYWNA));
  }
}
