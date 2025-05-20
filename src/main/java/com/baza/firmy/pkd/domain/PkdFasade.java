package com.baza.firmy.pkd.domain;

import com.baza.firmy.pkd.domain.dto.PkdDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PkdFasade {

  private final StworzPkdUseCase stworzPkdUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzPkd(PkdDto stworzPkdDto) {
    return stworzPkdUseCase.stworzPkd(stworzPkdDto);
  }
}
