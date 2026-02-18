package com.baza.firmy.common.harmonogram.scheduler;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Klasa jest rozszerzeniem dla klasy QuartzSzczegóły. Jest wykorzystywana dla zadań cyklicznych.
 */
@Data
@EqualsAndHashCode (callSuper=false)
class CronTriggerSzczegoly extends QuartzSzczegoly {

    private String wyrazenieCron;

    @Builder
    public CronTriggerSzczegoly(String identyfikator, String opis, String wyrazenieCron) {
        super(identyfikator, opis);
        this.wyrazenieCron = wyrazenieCron;
    }
}
