package com.baza.firmy.adresy.domain;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdresFacade {

  private final StworzAdresUseCase stworzAdresUseCase;

  @Transactional(TxType.MANDATORY)
  public UUID stworzAdres(AdresDto adresDto) {
    return stworzAdresUseCase.stworzAdres(adresDto);
  }
}
