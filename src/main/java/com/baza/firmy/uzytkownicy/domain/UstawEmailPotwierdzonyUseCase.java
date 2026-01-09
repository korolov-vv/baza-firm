package com.baza.firmy.uzytkownicy.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class UstawEmailPotwierdzonyUseCase {

  private final UzytkownicyRepository uzytkownicyRepository;

  public UUID ustawEmailPotwierdzony(String email) {
    UzytkownikEntity uzytkownikDoAktualizacji = uzytkownicyRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Użytkownik o podanym emailu nie istnieje: " + email));
    uzytkownikDoAktualizacji.setCzyEmailPotwierdzony(true);

    return uzytkownicyRepository.save(uzytkownikDoAktualizacji).getUuid();
  }
}
