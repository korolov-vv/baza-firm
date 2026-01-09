package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.uzytkownicy.dto.StworzUzytkownikaDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UzytkownicyFacade {

  private final StworzUzytkownikaUseCase stworzUzytkownikaUseCase;
  private final UstawEmailPotwierdzonyUseCase ustawEmailPotwierdzonyUseCase;

  @Transactional
  public UUID stworzUzytkownika(StworzUzytkownikaDto stworzUzytkownikaDto) {
    return stworzUzytkownikaUseCase.stworzUzytkownika(stworzUzytkownikaDto);
  }

  @Transactional
  public UUID ustawEmailPotwierdzony(String email) {
    return ustawEmailPotwierdzonyUseCase.ustawEmailPotwierdzony(email);
  }
}
