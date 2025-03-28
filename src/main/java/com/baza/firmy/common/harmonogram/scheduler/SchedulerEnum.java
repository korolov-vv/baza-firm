package com.baza.firmy.common.harmonogram.scheduler;

import lombok.Getter;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Klasa jest enumem zawierającym wszystkie typy zadań cyklicznych. Zadeklarowane są tutaj wszystkie typy zadań cyklicznych wraz z
 * częstotliwością wykonywania.
 */
@Getter
enum SchedulerEnum {
  TEST_SCHEDULER("TEST_TRIGGER", "TEST_JOB", "0 0/1 * * * ?", TestJob.class),
  POBIERAJ_LISTE_JDG_OST_DOBA_SCHEDULER("POBIERAJ_LISTE_JDG_OST_DOBA_TRIGGER", "POBIERAJ_LISTE_JDG_OST_DOBA_JOB", "0 20 10 * * ?", PobierajListeJDGOstDobaJob.class);

  private String triggerKod;
  private String jobKod;
  private String cron;
  private Class<? extends QuartzJobBean> klasa;

  SchedulerEnum(
      String triggerKod, String jobKod, String cron, Class<? extends QuartzJobBean> klasa) {
    this.triggerKod = triggerKod;
    this.jobKod = jobKod;
    this.cron = cron;
    this.klasa = klasa;
  }
}
