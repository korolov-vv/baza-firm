package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.domain.dto.StworzSubscrypcjeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzSubscrypcjeUseCase {

  private final SubscrypcjeRepository subscrypcjeRepository;
  private final SubscrypcjeMapper subscrypcjeMapper;

  public UUID stworzSubscrypcje(StworzSubscrypcjeDto stworzSubscrypcjeDto) {
    SubscrypcjaEntity subscrypcjaEntity = subscrypcjeMapper.toSubscrypcjaEntity(stworzSubscrypcjeDto);
    return subscrypcjeRepository.save(subscrypcjaEntity).getUuid();
  }
}
