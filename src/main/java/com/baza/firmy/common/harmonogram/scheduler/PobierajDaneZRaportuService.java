package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.PobierzDaneZRaportuService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PobierajDaneZRaportuService implements BazowySchedulerService {

  private final PobierzDaneZRaportuService pobierzDaneZRaportuService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    pobierzDaneZRaportuService.pobierzDaneZRaportu();
  }
}
