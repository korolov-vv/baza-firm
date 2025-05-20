package com.baza.firmy.jdg.domain;

import com.baza.firmy.jdg.domain.dto.JdgSzczegolyDto;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JdgFacade {

  private final StworzJdgUseCase stworzJdgUseCase;

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UUID stworzJdg(JdgSzczegolyDto jdgSzczegolyDto) {
    UUID savedUuid = stworzJdgUseCase.stworzJdg(jdgSzczegolyDto);
    log.info("Zapisano JDG: UUID = {}", savedUuid);
    return savedUuid;
  }
}
