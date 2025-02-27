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
class PobierajListeJdgOstDobaService implements BazowySchedulerService {

  private final QuartzManager quartzManager;
  private final PobierzDaneZCeidgService pobierzDaneZCeidgService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    quartzManager.usunZadanie(SchedulerSingleEnum.POBIERAJ_LISTE_JDG_SCHEDULER);

    LocalDate dataOd = LocalDate.now().minusDays(2L);
    pobierzDaneZCeidgService.pobierzListyJdgOstDobaIZapisz(Map.of(
        "status", "AKTYWNY", 
        "dataOd", dataOd.format(DateTimeFormatter.ISO_LOCAL_DATE)
    ), null);

    quartzManager.usunZadanie(SchedulerSingleEnum.POBIERAJ_LISTE_JDG_SCHEDULER);
  }
}

