package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodmiotyGospodarczeFacade {

  private final StworzPodmiotGospodarczyZJdgUseCase stworzPodmiotGospodarczyZJdgUseCase;

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UUID stworzPodmiotGospodarczy(JdgSzczegolyDto jdgSzczegolyDto) {
    UUID savedUuid = stworzPodmiotGospodarczyZJdgUseCase.stworzPodmiotGospodarczy(jdgSzczegolyDto);
    log.info("Zapisano JDG: UUID = {}", savedUuid);
    return savedUuid;
  }
}
