package com.baza.firmy.common.harmonogram.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Klasa jest Serwisem (element pośredniczący) związaną z testowym zadaniem w Quartz.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TestSchedulerService implements BazowySchedulerService {

  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    try {
      log.info("Start test job 1.0.0");
      TimeUnit.SECONDS.sleep(10);
      log.info("Koniec test job 1.0.0");
    } catch (InterruptedException exception) {
      log.error("Test job error", exception);
    }
  }
}
