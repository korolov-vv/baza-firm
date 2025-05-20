package com.baza.firmy.danezraportu.entity;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DaneZRaportuFacade {

  private final PobierzDaneZRaportuUseCase pobierzDaneZRaportuUseCase;
  private final ZapiszDaneZRaportuUseCase zapiszDaneZRaportuUseCase;

  public DaneZRaportuDto pobierzDaneDlaWojewodztwa(String wojewodztwo) {
    return pobierzDaneZRaportuUseCase.pobierzDaneDlaWojewodztwa(wojewodztwo);
  }

  @Transactional
  public UUID zapiszDane(DaneZRaportuDto daneZRaportuDto) {
    return zapiszDaneZRaportuUseCase.zapiszDane(daneZRaportuDto);
  }
}
