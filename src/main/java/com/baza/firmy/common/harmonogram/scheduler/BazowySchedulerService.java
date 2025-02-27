package com.baza.firmy.common.harmonogram.scheduler;

import org.quartz.JobExecutionContext;

/**
 * Klasa jest bazowym Serwisem (element pośredniczący) do implementacji dla serwisów zadań w Quartz.
 */
public interface BazowySchedulerService {

  String EXTERNAL_SOURCE = "externalSource";

  void executeScheduler(JobExecutionContext jobExecutionContext);

}
