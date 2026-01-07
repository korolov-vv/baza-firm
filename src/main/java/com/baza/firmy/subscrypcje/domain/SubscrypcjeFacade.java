package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.domain.dto.StworzSubscrypcjeDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscrypcjeFacade {

  private final StworzSubscrypcjeUseCase stworzSubscrypcjeUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzSubscrypcje(StworzSubscrypcjeDto stworzSubscrypcjeDto) {
    return stworzSubscrypcjeUseCase.stworzSubscrypcje(stworzSubscrypcjeDto);
  }
}
