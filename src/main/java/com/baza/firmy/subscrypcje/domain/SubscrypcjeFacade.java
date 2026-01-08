package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.dto.StworzSubscrypcjeDto;
import com.baza.firmy.uzytkownicy.dto.StworzSubscrypcjeDlaFirmyKlientaDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscrypcjeFacade {

  private final StworzSubscrypcjeUseCase stworzSubscrypcjeUseCase;
  private final StworzTrialDlaFirmyKlientaUseCase stworzTrialDlaFirmyKlientaUseCase;
  private final StworzSubscrypcjeDlaFirmyKlientaUseCase stworzSubscrypcjeDlaFirmyKlientaUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzSubscrypcje(StworzSubscrypcjeDto stworzSubscrypcjeDto) {
    return stworzSubscrypcjeUseCase.stworzSubscrypcje(stworzSubscrypcjeDto);
  }

  @Transactional(TxType.MANDATORY)
  public UUID stworzTrialDlaFirmyKlienta(UUID uuidFirmyKlienta) {
    return stworzTrialDlaFirmyKlientaUseCase.stworzTrialDlaFirmyKlienta(uuidFirmyKlienta);
  }

  @Transactional(TxType.MANDATORY)
  public UUID stworzSubscrypcjeDlaFirmyKlienta(StworzSubscrypcjeDlaFirmyKlientaDto stworzSubscrypcjeDlaFirmyKlientaDto) {
    return stworzSubscrypcjeDlaFirmyKlientaUseCase.stworzSubscrypcjeDlaFirmyKlienta(stworzSubscrypcjeDlaFirmyKlientaDto);
  }
}
