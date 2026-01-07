package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.subscrypcje.query.SubscrypcjaViewEntity;
import com.baza.firmy.subscrypcje.query.SubscrypcjeQueryFacade;
import com.baza.firmy.uzytkownicy.domain.dto.StworzSubscrypcjeUzytkownikaDto;
import com.baza.firmy.uzytkownicy.query.UzytkownicyQueryFacade;
import com.baza.firmy.uzytkownicy.query.UzytkownikViewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzSubscrypcjeUzytkownikaUseCase {

  private final UzytkownicySubscrypcjeRepository uzytkownicySubscrypcjeRepository;
  private final UzytkownicyQueryFacade uzytkownicyQueryFacade;
  private final SubscrypcjeQueryFacade subscrypcjeQueryFacade;

  public UUID stworzSubscrypcjeUzytkownika(StworzSubscrypcjeUzytkownikaDto stworzSubscrypcjeUzytkownikaDto) {
    UzytkownikViewEntity uzytkownik = uzytkownicyQueryFacade.findByUuid(stworzSubscrypcjeUzytkownikaDto.getUzytkownikId())
            .orElseThrow(() -> new IllegalArgumentException("Uzytkownik o UUID: " + stworzSubscrypcjeUzytkownikaDto.getUzytkownikId() + " nie istnieje"));
    SubscrypcjaViewEntity subscrypcja = subscrypcjeQueryFacade.findByUuid(stworzSubscrypcjeUzytkownikaDto.getSubscrypcjaId())
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja o UUID: " + stworzSubscrypcjeUzytkownikaDto.getSubscrypcjaId() + " nie istnieje"));

    UzytkownikSubscrypcjaEntity uzytkownikEntity = stworzSubscrypcjeUzytkownika(uzytkownik, subscrypcja);

    return uzytkownicySubscrypcjeRepository.save(uzytkownikEntity).getUuid();
  }

  private UzytkownikSubscrypcjaEntity stworzSubscrypcjeUzytkownika(UzytkownikViewEntity uzytkownik, SubscrypcjaViewEntity subscrypcja) {
    return UzytkownikSubscrypcjaEntity.builder()
            .uzytkownik(uzytkownik)
            .subscrypcja(subscrypcja)
            .aktywnaOd(LocalDate.now())
            .aktywnaDo(LocalDate.now().plusDays(subscrypcja.getOkresTrwaniaWDniach()))
            .czyOplacona(false)
            .build();
  }
}
