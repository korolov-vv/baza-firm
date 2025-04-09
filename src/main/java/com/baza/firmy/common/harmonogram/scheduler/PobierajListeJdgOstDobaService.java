package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.service.PobierzDaneZCeidgService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class PobierajListeJdgOstDobaService implements BazowySchedulerService {

  private final QuartzManager quartzManager;
  private final PobierzDaneZCeidgService pobierzDaneZCeidgService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    log.info("Start POBIERAJ_LISTE_JDG_OST_DOBA_JOB");
    pobierzDaneZCeidgService.pobierzListyJdgNoweIZapisz(Map.of(
        "status", "AKTYWNY"
    ), null);

    quartzManager.stworzZadanieSchedulera(SchedulerSingleEnum.POBIERAJ_SZCZEGOLY_JDG_SCHEDULER);
    log.info("Completed POBIERAJ_LISTE_JDG_OST_DOBA_JOB");
  }
}

