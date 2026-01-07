package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.subscrypcje.domain.SubscrypcjeFacade;
import com.baza.firmy.uzytkownicy.domain.dto.UzytkownikDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzUzytkownikaUseCase {

  private final UzytkownicyRepository uzytkownicyRepository;
  private final UzytkownicyMapper uzytkownicyMapper;
  private final SubscrypcjeFacade subscrypcjeFacade;

  public UUID stworzUzytkownika(UzytkownikDto uzytkownikDto) {
    UzytkownikEntity uzytkownikEntity = uzytkownicyMapper.toUzytkownikEntity(uzytkownikDto);
    UUID uuidZapisanegoUzytkownika = uzytkownicyRepository.save(uzytkownikEntity).getUuid();

    subscrypcjeFacade.stworzTrialUzytkownika(uuidZapisanegoUzytkownika);

    return uuidZapisanegoUzytkownika;
  }
}
