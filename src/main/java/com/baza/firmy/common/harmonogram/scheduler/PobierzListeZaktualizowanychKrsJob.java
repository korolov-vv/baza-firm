package com.baza.firmy.common.harmonogram.scheduler;

public class PobierzListeZaktualizowanychKrsJob extends BazowyJob<PobierzListeZaktualizowanychKrsService> {

    public PobierzListeZaktualizowanychKrsJob(PobierzListeZaktualizowanychKrsService schedulerService) {
        super(schedulerService);
    }
}
