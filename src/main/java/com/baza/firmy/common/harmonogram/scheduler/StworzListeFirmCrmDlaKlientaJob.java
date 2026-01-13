package com.baza.firmy.common.harmonogram.scheduler;


class StworzListeFirmCrmDlaKlientaJob extends BazowyJob<StworzListeFirmCrmDlaKlientaService> {

  public StworzListeFirmCrmDlaKlientaJob(StworzListeFirmCrmDlaKlientaService schedulerService) {
    super(schedulerService);
  }
}
