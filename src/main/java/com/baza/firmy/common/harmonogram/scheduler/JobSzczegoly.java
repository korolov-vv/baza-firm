package com.baza.firmy.common.harmonogram.scheduler;

import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartz.JobDataMap;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Klasa jest rozszerzeniem dla klasy QuartzSzczegóły. Jest wykorzystywana dla zadań pojedynczych.
 */
@Data
@EqualsAndHashCode (callSuper=false)
class JobSzczegoly extends QuartzSzczegoly {

    private  Class<? extends QuartzJobBean> klasaZadania;
    private Optional<JobDataMap> jobDataMap;

    @Builder
    public JobSzczegoly(String identyfikator, String opis, Class<? extends QuartzJobBean> klasaZadania, JobDataMap jobDataMap) {
        super(identyfikator, opis);
        this.klasaZadania = klasaZadania;
        this.jobDataMap = Optional.ofNullable(jobDataMap);
    }
}
