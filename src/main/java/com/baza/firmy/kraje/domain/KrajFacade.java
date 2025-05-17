package com.baza.firmy.kraje.domain;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KrajFacade {

  private final StworzKrajeUseCase stworzKrajeUseCase;

  @Transactional(TxType.MANDATORY)
  public List<UUID> stworzKraje(List<KrajDto> krajDtos) {
    return stworzKrajeUseCase.stworzKraje(krajDtos);
  }
}
