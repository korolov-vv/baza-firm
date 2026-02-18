package com.baza.firmy.common.harmonogram.scheduler;


import java.sql.Date;
import java.util.UUID;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

/**
 * Klasa jest Serwisem (element pośredniczący) związaną z tworzeniem zadań w Quartz. Powstała w celu
 * wydzielenia wspólnej logiki dla tworzenia zadań w Quartz.
 */
@Service
class QuartzBuilderService {

  public JobDetail buildJobDetail(JobSzczegoly szczegoly) {
    JobBuilder jobbuilder =
        JobBuilder.newJob(szczegoly.getKlasaZadania())
            .withIdentity(szczegoly.getIdentyfikator())
            .withDescription(szczegoly.getOpis())
            .storeDurably();
    addJobDataMap(szczegoly, jobbuilder);
    return jobbuilder.build();
  }

  private void addJobDataMap(JobSzczegoly szczegoly, JobBuilder jobbuilder) {
    szczegoly.getJobDataMap().ifPresent(jobbuilder::usingJobData);
  }

  public Trigger buildCronJobTrigger(JobDetail jobDetail, CronTriggerSzczegoly szczegoly) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(szczegoly.getIdentyfikator())
        .withDescription(szczegoly.getOpis())
        .withSchedule(CronScheduleBuilder.cronSchedule(szczegoly.getWyrazenieCron()))
        .build();
  }

  public Trigger buildSimpleJobTrigger(JobDetail jobDetail, JobDataMap jobData, QuartzSzczegoly szczegoly) {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .usingJobData(jobData)
        .withIdentity(UUID.randomUUID().toString(), szczegoly.getIdentyfikator())
        .withDescription(szczegoly.getOpis())
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
        .build();
  }

  public Trigger buildDateTimeJobTrigger(JobDetail jobDetail, JobDataMap jobData, QuartzSzczegoly szczegoly, Long minutes) {
    Date startTime = new Date(System.currentTimeMillis() + minutes * 60 * 1000L);
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .usingJobData(jobData)
        .withIdentity(szczegoly.getIdentyfikator())
        .startAt(startTime)
        .withDescription(szczegoly.getOpis())
        .build();
  }
}
