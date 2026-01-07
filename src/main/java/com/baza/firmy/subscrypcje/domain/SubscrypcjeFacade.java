package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.subscrypcje.domain.dto.StworzSubscrypcjeDto;
import com.baza.firmy.uzytkownicy.domain.dto.StworzSubscrypcjeUzytkownikaDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscrypcjeFacade {

  private final StworzSubscrypcjeUseCase stworzSubscrypcjeUseCase;
  private final StworzTrialUzytkownikaUseCase stworzTrialUzytkownikaUseCase;
  private final StworzSubscrypcjeUzytkownikaUseCase stworzSubscrypcjeUzytkownikaUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzSubscrypcje(StworzSubscrypcjeDto stworzSubscrypcjeDto) {
    return stworzSubscrypcjeUseCase.stworzSubscrypcje(stworzSubscrypcjeDto);
  }

  @Transactional(TxType.MANDATORY)
  public UUID stworzTrialUzytkownika(UUID uuidUzytkownika) {
    return stworzTrialUzytkownikaUseCase.stworzTrialUzytkownika(uuidUzytkownika);
  }

  @Transactional(TxType.MANDATORY)
  public UUID stworzSubscrypcjeUzytkownika(StworzSubscrypcjeUzytkownikaDto stworzSubscrypcjeUzytkownikaDto) {
    return stworzSubscrypcjeUzytkownikaUseCase.stworzSubscrypcjeUzytkownika(stworzSubscrypcjeUzytkownikaDto);
  }
}
