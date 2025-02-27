package com.baza.firmy.common.harmonogram.scheduler;

import lombok.Getter;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Klasa jest enumem zawierającym wszystkie typy zadań pojedynczych.
 */
@Getter
public enum SchedulerSingleEnum {

  POBIERAJ_LISTE_JDG_SCHEDULER("POBIERAJ_LISTE_JDG_TRIGGER", "POBIERAJ_LISTE_JDG_JOB", PobierajListeJDGJob.class);

  private String triggerKod;
  private String jobKod;
  private Class<? extends QuartzJobBean> klasa;

  SchedulerSingleEnum(
      String triggerKod, String jobKod, Class<? extends QuartzJobBean> klasa) {
    this.triggerKod = triggerKod;
    this.jobKod = jobKod;
    this.klasa = klasa;
  }
}
