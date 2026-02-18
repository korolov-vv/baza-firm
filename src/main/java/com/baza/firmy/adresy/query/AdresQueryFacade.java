package com.baza.firmy.adresy.query;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdresQueryFacade {

  private final AdresQueryRepository adresQueryRepository;

  public AdresViewEntity getAdresPoUuid(UUID uuid) {
    return adresQueryRepository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("No adres found with uuid " + uuid));
  }
}
