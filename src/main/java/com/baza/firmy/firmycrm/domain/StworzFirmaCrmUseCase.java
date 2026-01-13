package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class StworzFirmaCrmUseCase {

  private final FirmyCrmRepository firmyCrmRepository;

  public UUID stworzFirmaCrm(PodmiotGospodarczyViewEntity firmaKlient, PodmiotGospodarczyViewEntity firmaCrm) {
    // Check if FirmaCrm already exists for this client-firm pair
    if (firmyCrmRepository.existsByFirmaKlientAndFirmaCrm(firmaKlient, firmaCrm)) {
      log.debug("FirmaCrm już istnieje aktywna Subscrypcja dla klienta: {} i firmy: {}. Pomijam tworzenie.",
              firmaKlient.getUuid(), firmaCrm.getUuid());
      return null;
    }

    FirmaCrmEntity firmaCrmEntity = FirmaCrmEntity.builder()
            .uuid(UUID.randomUUID())
            .firmaKlient(firmaKlient)
            .firmaCrm(firmaCrm)
            .statusKontaktu(StatusKontaktu.DO_KONTAKTU)
            .liczbaProbKontaktu(0)
            .build();

    FirmaCrmEntity saved = firmyCrmRepository.save(firmaCrmEntity);
    log.debug("Utworzono FirmaCrm: {} dla klienta: {}", saved.getUuid(), firmaKlient.getUuid());
    return saved.getUuid();
  }
}

