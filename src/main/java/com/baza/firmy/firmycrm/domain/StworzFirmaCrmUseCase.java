package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class StworzFirmaCrmUseCase {

  private final FirmyCrmRepository firmyCrmRepository;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;

  public UUID stworzFirmaCrm(UUID firmaKlientUuid, UUID firmaCrmUuid) {
    if (firmaKlientUuid == firmaCrmUuid) {
      log.debug("FirmaCrm jest ta sama co firma klienta: {} == {}. Pomijam tworzenie.",
              firmaKlientUuid, firmaCrmUuid);
    }
    // Check if FirmaCrm already exists for this client-firm pair
    if (firmyCrmRepository.existsByFirmaKlientUuidAndFirmaCrmUuid(firmaKlientUuid, firmaCrmUuid)) {
      log.debug("FirmaCrm już istnieje aktywna Subscrypcja dla klienta: {} i firmy: {}. Pomijam tworzenie.",
              firmaKlientUuid, firmaCrmUuid);
      return null;
    }

    PodmiotGospodarczyViewEntity firmaKlient = podmiotyGospodarczeQueryFacade.pobierzPoUuid(firmaKlientUuid)
            .orElseThrow(() -> new IllegalStateException("Nie udało się pobrać firmy klienta o UUID: " + firmaKlientUuid));

    PodmiotGospodarczyViewEntity firmaCrm = podmiotyGospodarczeQueryFacade.pobierzPoUuid(firmaCrmUuid)
            .orElseThrow(() -> new IllegalStateException("Nie udało się pobrać firmy CRM o UUID: " + firmaCrmUuid));

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

