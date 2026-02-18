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
  POBIERAJ_LISTE_JDG_NOWE_SCHEDULER("POBIERAJ_LISTE_JDG_NOWE_TRIGGER", "POBIERAJ_LISTE_JDG_NOWE_JOB", "0 0 2 * * ?", PobierajListeJdgNoweJob.class),
  EKSPORTUJ_LISTE_JDG_NOWE_SCHEDULER("EKSPORTUJ_LISTE_JDG_NOWE_TRIGGER", "EKSPORTUJ_LISTE_JDG_NOWE_JOB", "0 0 7 * * ?", EksportujListeJdgNoweJob.class),
  WYSLIJ_LISTE_JDG_NOWE_SCHEDULER("WYSLIJ_LISTE_JDG_NOWE_TRIGGER", "WYSLIJ_LISTE_JDG_NOWE_JOB", "0 0 8 * * ?", WyslijListeJdgNoweJob.class),
  POBIERZ_LISTE_ZAKTUALIZOWANYCH_KRS_SCHEDULER("POBIERZ_LISTE_ZAKTUALIZOWANYCH_KRS_TRIGGER", "POBIERZ_LISTE_ZAKTUALIZOWANYCH_KRS_JOB", "0 30 1 * * ?", PobierzListeZaktualizowanychKrsJob.class),
  POBIERAJ_DANE_ZAKTUALIZOWANYCH_PODMIOTOW_Z_KRS("POBIERAJ_DANE_ZAKTUALIZOWANYCH_PODMIOTOW_Z_KRS_TRIGGER", "POBIERAJ_DANE_ZAKTUALIZOWANYCH_PODMIOTOW_Z_KRS_JOB", "0 * * * * ?", PobierajDaneZaktualizowanychFirmZKrsJob.class),
//  POBIERAJ_DANE_FIRM_Z_PORTALA_ZEWN("POBIERAJ_DANE_FIRM_Z_PORTALA_ZEWN_TRIGGER", "POBIERAJ_DANE_FIRM_Z_PORTALA_ZEWN_JOB", "0 * * * * ?", PobierajDaneFirmZPortalaZewnJob.class),
//  PARSE_PORTAL_ZEWN("PARSE_PORTAL_ZEWN_TRIGGER", "PARSE_PORTAL_ZEWN_JOB", "0 * * * * ?", ParsePortalZewnJob.class),
  EKSPORTUJ_NOWE_FIRMY_DO_CRM_SCHRACK_SCHEDULER("EKSPORTUJ_NOWE_FIRMY_DO_CRM_SCHRACK_SCHEDULER_TRIGGER", "EKSPORTUJ_NOWE_FIRMY_DO_CRM_SCHRACK_SCHEDULER_JOB", "0 0 7 * * ?", EksportujNoweFirmyDoCrmSchrackJob.class);

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
