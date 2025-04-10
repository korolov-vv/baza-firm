package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.service.PobierzDaneZCeidgService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class PobierajListeJdgNoweService implements BazowySchedulerService {

  private final QuartzManager quartzManager;
  private final PobierzDaneZCeidgService pobierzDaneZCeidgService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    log.info("Start POBIERAJ_LISTE_JDG_OST_DOBA_JOB");
    pobierzDaneZCeidgService.pobierzListyJdgNoweIZapisz(Map.of(
        "status", "AKTYWNY",
        "dataOd", LocalDate.now().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE),
        "dataDo", LocalDate.now().minusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE)
    ), null);

    quartzManager.stworzZadanieSchedulera(SchedulerSingleEnum.POBIERAJ_SZCZEGOLY_JDG_SCHEDULER);
    log.info("Completed POBIERAJ_LISTE_JDG_OST_DOBA_JOB");
  }
}

