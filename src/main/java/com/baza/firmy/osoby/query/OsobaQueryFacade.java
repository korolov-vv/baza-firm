package com.baza.firmy.osoby.query;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OsobaQueryFacade {

  private final OsobaQueryRepository osobaQueryRepository;

  public OsobaViewEntity findByUuid(UUID uuid) {
    return osobaQueryRepository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Osoba not found"));
  }

  public Optional<OsobaViewEntity> findByNip(String nip) {
      return osobaQueryRepository.findByNip(nip);
  }
}
