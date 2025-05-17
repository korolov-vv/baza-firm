package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.PobierzDaneZCeidgService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class PobierajListeJdgService implements BazowySchedulerService {

  private final PobierzDaneZCeidgService pobierzDaneZCeidgService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    pobierzDaneZCeidgService.pobierzListyJdgWsteczIZapisz(Map.of("status", "AKTYWNY"), null);
  }
}
