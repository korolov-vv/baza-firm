package com.baza.firmy.common.harmonogram.scheduler;

public class PobierajDaneZaktualizowanychFirmZKrsJob extends BazowyJob<PobierajDaneZaktualizowanychFirmZKrsService> {

  public PobierajDaneZaktualizowanychFirmZKrsJob(PobierajDaneZaktualizowanychFirmZKrsService schedulerService) {
    super(schedulerService);
  }
}
