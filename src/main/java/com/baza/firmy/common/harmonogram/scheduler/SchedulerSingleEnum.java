package com.baza.firmy.common.harmonogram.scheduler;

import lombok.Getter;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Klasa jest enumem zawierającym wszystkie typy zadań pojedynczych.
 */
@Getter
public enum SchedulerSingleEnum {

  POBIERAJ_LISTE_JDG_SCHEDULER("POBIERAJ_LISTE_JDG_TRIGGER", "POBIERAJ_LISTE_JDG_JOB", PobierajListeJDGJob.class),
  POBIERAJ_SZCZEGOLY_JDG_SCHEDULER("POBIERAJ_SZCZEGOLY_JDG_TRIGGER", "POBIERAJ_SZCZEGOLY_JDG_JOB", PobierajSzczegolyJDGJob.class),
  POBIERAJ_BRAKUJACE_DANE("POBIERAJ_BRAKUJACE_DANE_TRIGGER", "POBIERAJ_BRAKUJACE_DANE_JOB", PobierajBrakujaceDaneJob.class),
  POBIERAJ_DANE_Z_RAPORTU("POBIERAJ_DANE_Z_RAPORTU_TRIGGER", "POBIERAJ_DANE_Z_RAPORTU_JOB", PobierajDaneZRaportuJob.class),
  SCHRACK_LISTA_FIRM_SCHEDULER("SCHRACK_LISTA_FIRM_TRIGGER", "SCHRACK_LISTA_FIRM_JOB", SchrackListaFirmJob.class),
  STWORZ_LISTE_FIRM_CRM_DLA_KLIENTA_SCHEDULER("STWORZ_LISTE_FIRM_CRM_DLA_KLIENTA_SCHEDULER", "STWORZ_LISTE_FIRM_CRM_DLA_KLIENTA_JOB", StworzListeFirmCrmDlaKlientaJob.class);

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
