package com.baza.firmy.danezkrs.domain;

import com.baza.firmy.common.service.KrsService;
import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class PobierzListeZaktualizowanychWpisowUseCase {

  private final KrsService krsService;
  private final ListaZaktualizowanychWpisowKrsMapper mapper;
  private final ListaZaktualizowanychWpisowKrsRepository repository;

  public UUID pobierzOrazZapiszListeZaktualizowanychWpisow(LocalDate data, int godzinaOd, int godzinaDo) {
    try {
      final ListaZmienionychWpisowKrsResponse response = krsService.pobierzListeZmienionychWpisow(data, godzinaOd, godzinaDo);
      if (response == null) {
        log.warn("Otrzymano pustą odpowiedź z KRS dla daty: {}, godzinaOd: {}, godzinaDo: {}", data, godzinaOd, godzinaDo);
        return null;
      }
      if (response.numeryKrs().isEmpty()) {
        log.info("Brak zaktualizowanych wpisów KRS dla daty: {}, godzinaOd: {}, godzinaDo: {}", data, godzinaOd, godzinaDo);
        return null;
      }

      return repository.save(mapper.toEntity(response)).getUuid();
    } catch (Exception e) {
      log.error("Błąd podczas pobierania listy zaktualizowanych wpisów KRS", e);
      throw new RuntimeException("Nie udało się pobrać listy zaktualizowanych wpisów KRS", e);
    }
  }
}
