package com.baza.firmy.common.harmonogram.scheduler;

public class EksportujNoweFirmyDoCrmSchrackJob extends BazowyJob<EksportujNoweFirmDoCrmSchrackService> {

  public EksportujNoweFirmyDoCrmSchrackJob(EksportujNoweFirmDoCrmSchrackService schedulerService) {
    super(schedulerService);
  }
}
