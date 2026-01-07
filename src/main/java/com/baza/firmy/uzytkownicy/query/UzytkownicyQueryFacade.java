package com.baza.firmy.uzytkownicy.query;

import com.baza.firmy.uzytkownicy.domain.dto.UzytkownikDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UzytkownicyQueryFacade {

  private final UzytkownicyQueryRepository uzytkownicyQueryRepository;
  private final UzytkownicyQueryMapper uzytkownicyQueryMapper;

  public UzytkownikDto findByUuid(UUID uuid) {
    return uzytkownicyQueryRepository.findByUuid(uuid)
            .map(uzytkownicyQueryMapper::toUzytkownikDto)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika o UUID: " + uuid));
  }

  public Optional<UzytkownikDto> findByEmail(String email) {
      return uzytkownicyQueryRepository.findByEmail(email)
              .map(uzytkownicyQueryMapper::toUzytkownikDto);
  }
}
