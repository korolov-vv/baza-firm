package com.baza.firmy.danezraportu.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PobierzDaneZRaportuUseCase {

  private final DaneZRaportuRepository daneZRaportuRepository;
  private final DaneZRaportuMapper daneZRaportuMapper;

  DaneZRaportuDto pobierzDaneDlaWojewodztwa(String wojewodztwo) {
    return daneZRaportuRepository.findByWojewodztwo(wojewodztwo)
        .map(daneZRaportuMapper::toDto)
        .orElseThrow(() -> new RuntimeException("Dane z raportu nie znalezione"));
  }
}
