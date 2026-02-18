package com.baza.firmy.subscrypcje.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscrypcjeQueryFacade {

  private final SubscrypcjeQueryRepository subscrypcjeQueryRepository;

  public Optional<SubscrypcjaViewEntity> findByUuid(UUID uuid) {
    return subscrypcjeQueryRepository.findByUuid(uuid);
  }

  public Optional<SubscrypcjaViewEntity> findByNazwa(String email) {
      return subscrypcjeQueryRepository.findByNazwa(email);
  }
}
