package com.baza.firmy.uzytkownicy.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UzytkownicyQueryFacade {

  private final UzytkownicyQueryRepository uzytkownicyQueryRepository;

  public Optional<UzytkownikViewEntity> findByUuid(UUID uuid) {
    return uzytkownicyQueryRepository.findByUuid(uuid);
  }

  public Optional<UzytkownikViewEntity> findByEmail(String email) {
      return uzytkownicyQueryRepository.findByEmail(email);
  }
}
