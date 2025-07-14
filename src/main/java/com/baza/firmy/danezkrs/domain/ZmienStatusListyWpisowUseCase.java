package com.baza.firmy.danezkrs.domain;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ZmienStatusListyWpisowUseCase {

  private final ListaZaktualizowanychWpisowKrsRepository repository;

  @Transactional
  public void zmienStatusListyWpisowUseCase(UUID uuid, StatusPobieraniaEnum status) {
    ListaZaktualizowanychWpisowKrsEntity doAktualizacji = repository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono listy zaktualizowanych wpisów KRS o UUID: " + uuid));

    doAktualizacji.setStatusPobierania(status);
    repository.save(doAktualizacji);
  }

  @Transactional
  public void zmienStatusListyWpisowUseCase(UUID uuid, StatusPobieraniaEnum status, List<String> listaNiepobranychWpisow) {
    ListaZaktualizowanychWpisowKrsEntity doAktualizacji = repository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono listy zaktualizowanych wpisów KRS o UUID: " + uuid));

    doAktualizacji.getNieobsluzoneKrsy().clear();
    doAktualizacji.getNieobsluzoneKrsy().addAll(listaNiepobranychWpisow);
    doAktualizacji.setStatusPobierania(status);
    repository.save(doAktualizacji);
  }
}
