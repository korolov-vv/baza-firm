package com.baza.firmy.common.harmonogram.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Klasa jest bazowym Serwisem zadań (element pośredniczący) dla serwisów tworzących zadania w Quartz.
 */
class BazowyJob<T extends BazowySchedulerService> extends QuartzJobBean {

  protected final T schedulerService;

  public BazowyJob(T schedulerService) {
    this.schedulerService = schedulerService;
  }

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext)
      throws JobExecutionException {
    schedulerService.executeScheduler(jobExecutionContext);
  }
}
