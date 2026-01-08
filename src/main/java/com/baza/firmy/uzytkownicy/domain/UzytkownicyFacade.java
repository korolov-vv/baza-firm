package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.uzytkownicy.dto.StworzUzytkownikaDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UzytkownicyFacade {

  private final StworzUzytkownikaUseCase stworzUzytkownikaUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzUzytkownika(StworzUzytkownikaDto stworzUzytkownikaDto) {
    return stworzUzytkownikaUseCase.stworzUzytkownika(stworzUzytkownikaDto);
  }
}
