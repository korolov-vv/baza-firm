package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.uzytkownicy.domain.dto.StworzUzytkownikaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzUzytkownikaUseCase {

  private final UzytkownicyRepository uzytkownicyRepository;
  private final UzytkownicyMapper uzytkownicyMapper;

  public UUID stworzUzytkownika(StworzUzytkownikaDto stworzUzytkownikaDto) {
    UzytkownikEntity uzytkownikEntity = uzytkownicyMapper.toUzytkownikEntity(stworzUzytkownikaDto);
    return uzytkownicyRepository.save(uzytkownikEntity).getUuid();
  }
}
