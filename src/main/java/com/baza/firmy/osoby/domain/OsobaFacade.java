package com.baza.firmy.osoby.domain;

import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OsobaFacade {

  private final StworzOsobeUseCase stworzOsobeUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzOsobe(StworzWlascicielaDto wlascicielDto) {
    return stworzOsobeUseCase.stworzOsobe(wlascicielDto);
  }
}
