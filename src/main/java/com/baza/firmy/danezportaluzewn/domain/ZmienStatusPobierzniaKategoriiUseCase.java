package com.baza.firmy.danezportaluzewn.domain;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class ZmienStatusPobierzniaKategoriiUseCase {

  private final PortalZewnKategoriaRepository repository;

  @Transactional
  public void zmienStatusPobierzniaKategorii(UUID uuid, StatusPobieraniaEnum status) {
    PortalZewnKategoriaEntity doAktualizacji = repository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono kategorii portalu zewnętrznego o UUID: " + uuid));

    doAktualizacji.setStatusPobierania(status);
    repository.save(doAktualizacji);
  }

  @Transactional
  public void zmienStatusPobierzniaKategorii(UUID uuid, StatusPobieraniaEnum status, String blad) {
      PortalZewnKategoriaEntity doAktualizacji = repository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono kategorii portalu zewnętrznego o UUID: " + uuid));

    doAktualizacji.setBlad(blad);
    doAktualizacji.setStatusPobierania(status);
    repository.save(doAktualizacji);
  }
}
