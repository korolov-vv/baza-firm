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
  POBIERAJ_LISTE_JDG_NOWE_SCHEDULER("POBIERAJ_LISTE_JDG_NOWE_TRIGGER", "POBIERAJ_LISTE_JDG_NOWE_JOB", "0 0 3 * * ?", PobierajListeJdgNoweJob.class),
  EKSPORTUJ_LISTE_JDG_NOWE_SCHEDULER("EKSPORTUJ_LISTE_JDG_NOWE_TRIGGER", "EKSPORTUJ_LISTE_JDG_NOWE_JOB", "0 0 7 * * ?", EksportujListeJdgNoweJob.class),
  WYSLIJ_LISTE_JDG_NOWE_SCHEDULER("WYSLIJ_LISTE_JDG_NOWE_TRIGGER", "WYSLIJ_LISTE_JDG_NOWE_JOB", "0 0 9 * * ?", WyslijListeJdgNoweJob.class);

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
