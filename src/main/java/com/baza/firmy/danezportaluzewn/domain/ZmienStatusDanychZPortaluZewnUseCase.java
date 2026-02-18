package com.baza.firmy.danezportaluzewn.domain;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ZmienStatusDanychZPortaluZewnUseCase {

  private final PortalZewnRepository repository;

  @Transactional
  public void zmienStatusDanychZPortaluZewn(UUID uuid, StatusPobieraniaEnum status) {
    PortalZewnEntity doAktualizacji = repository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono danych z portalu zewnętrznego o UUID: " + uuid));

    doAktualizacji.setStatusPobierania(status);
    repository.save(doAktualizacji);
  }

  @Transactional
  public void zmienStatusListyWpisowUseCase(UUID uuid, StatusPobieraniaEnum status, List<FirmaPortalZewnDto> listaNiepobranychFirm) {
    PortalZewnEntity doAktualizacji = repository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono danych z portalu zewnętrznego o UUID: " + uuid));

    if (doAktualizacji.getNiepobraneFirmy() != null) {
      doAktualizacji.getNiepobraneFirmy().clear();
    } else {
      doAktualizacji.setNiepobraneFirmy(new ArrayList<>());
    }

    doAktualizacji.getNiepobraneFirmy().addAll(listaNiepobranychFirm);
    doAktualizacji.setStatusPobierania(status);
    repository.save(doAktualizacji);
  }
}
