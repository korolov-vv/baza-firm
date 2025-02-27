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
class PobierajListeJdgService implements BazowySchedulerService {

  private final PobierzDaneZCeidgService pobierzDaneZCeidgService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    pobierzDaneZCeidgService.pobierzListyJdgWsteczIZapisz(Map.of("status", "AKTYWNY"), null);
  }
}

