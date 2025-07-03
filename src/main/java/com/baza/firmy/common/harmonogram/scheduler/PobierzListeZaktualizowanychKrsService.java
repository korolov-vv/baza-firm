package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.KrsService;
import com.baza.firmy.danezkrs.domain.ListaZaktualizowanychKrsFacade;
import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PobierzListeZaktualizowanychKrsService implements BazowySchedulerService {

  private final ListaZaktualizowanychKrsFacade listaZaktualizowanychKrsFacade;
  private final KrsService krsService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    try {
      final ListaZmienionychWpisowKrsResponse response = krsService.pobierzListeZmienionychWpisow(LocalDate.now().minusDays(2), 0, 23);
      if (response == null) {
        log.warn("Otrzymano pustą odpowiedź z KRS dla daty: {}, godzinaOd: {}, godzinaDo: {}",
            LocalDate.now().minusDays(2), 0, 23);
        return;
      }
      if (response.numeryKrs().isEmpty()) {
        log.info("Brak zaktualizowanych wpisów KRS dla daty: {}, godzinaOd: {}, godzinaDo: {}",
            LocalDate.now().minusDays(2), 0, 23);
        return;
      }

      listaZaktualizowanychKrsFacade.zapiszListeZaktualizowanychWpisow(response);
    } catch (Exception e) {
      log.error("Błąd podczas pobierania listy zaktualizowanych wpisów KRS", e);
      throw new RuntimeException("Nie udało się pobrać listy zaktualizowanych wpisów KRS", e);
    }
  }
}

