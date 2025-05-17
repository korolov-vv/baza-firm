package com.baza.firmy.kraje.domain;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KrajFacade {

  private final StworzKrajeUseCase stworzKrajUseCase;

  public List<UUID> stworzKraje(List<KrajDto> krajDtos) {
    return stworzKrajUseCase.stworzKraje(krajDtos);
  }
}
