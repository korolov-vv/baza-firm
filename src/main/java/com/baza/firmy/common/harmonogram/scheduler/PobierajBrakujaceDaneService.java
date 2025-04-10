package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.service.PobierzDaneZCeidgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class PobierajBrakujaceDaneService implements BazowySchedulerService {

 private final PobierzDaneZCeidgService pobierzDaneZCeidgService;

 @Override
 public void executeScheduler(JobExecutionContext jobExecutionContext) {
  log.info("Start POBIERAJ_BRAKUJACE_DANE_JOB");
  pobierzDaneZCeidgService.pobierzBrakujaceDane();
 }
}
