package com.baza.firmy.common.harmonogram.scheduler;

import static com.baza.firmy.common.harmonogram.scheduler.BazowySchedulerService.EXTERNAL_SOURCE;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Klasa jest Serwisem (element pośredniczący) związaną z planowaniem i tworzeniem zadań w Quartz. Powstała w celu wydzielenia
 * wspólnej logiki dla planowania i tworzenia zadań pojedynczych oraz cyklicznych.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QuartzManager implements InitializingBean {

  private final QuartzBuilderService quartzBuilderService;
  private final Scheduler scheduler;

  @Override
  public void afterPropertiesSet() {
    zaplanujWszystkieZadania();
  }

  private void zaplanujWszystkieZadania() {
    Arrays.stream(SchedulerEnum.values()).forEach(this::zaplanujZadanieScheduleraZOpoznieniem);
  }

  private void zaplanujZadanieScheduleraZOpoznieniem(SchedulerEnum schedulerEnum) {
    try {
      if (StringUtils.isBlank(schedulerEnum.getCron())) {
        return;
      }
      JobDetail jobDetail = stworzJobDetail(schedulerEnum);
      Trigger trigger = stworzCronTrigger(schedulerEnum, jobDetail);
      scheduler.scheduleJob(jobDetail, trigger);
      log.info("---------------------------------------------------- Scheduler został dodany: {}, with cron{}", schedulerEnum.getTriggerKod(), schedulerEnum.getCron());
    } catch (ObjectAlreadyExistsException ex) {
      log.info(
          "!!!  Pominięto dodawanie triggera - obiekt już istnieje: {}", schedulerEnum.getTriggerKod());
    } catch (SchedulerException ex) {
      log.error("Błąd planowania zadań", ex);
    }
  }

  public void zaplanujZadanieScheduleraZOpoznieniem(SchedulerSingleEnum schedulerEnum, Map<?,?> map, Long minutes) {
    try {
      UUID uuid = UUID.randomUUID();
      JobDetail jobDetail = stworzJobDetail(schedulerEnum, uuid);
      JobDataMap jobData = new JobDataMap(map);

      Trigger trigger = stworzDateTrigger(schedulerEnum, jobDetail, jobData, minutes, uuid);
      scheduler.scheduleJob(jobDetail, trigger);
      LocalDateTime triggerTime =
          LocalDateTime.ofInstant(Instant.ofEpochMilli(trigger.getStartTime().getTime()),
              TimeZone.getDefault().toZoneId());
      log.info("---------------------------------------------------- Scheduler został dodany: {}. Uruchomienie o : {}", jobDetail.getKey(), triggerTime);
    } catch (ObjectAlreadyExistsException ex) {
      log.info("!!!  Pominięto dodawanie triggera - obiekt już istnieje: {}", schedulerEnum.getTriggerKod());
    } catch (SchedulerException ex) {
      log.error("Błąd planowania zadań", ex);
    }
  }

  public void stworzZadanieSchedulera(
      SchedulerSingleEnum schedulerEnum, Long id
  ) {
    try {
      UUID uuid = UUID.randomUUID();
      JobDataMap jobData = new JobDataMap(Map.of(EXTERNAL_SOURCE, id));
      JobDetail jobDetail = stworzJobDetail(schedulerEnum,uuid);
      Trigger trigger = stworzSimpleTrigger(schedulerEnum, jobDetail, jobData, uuid);
      scheduler.scheduleJob(jobDetail, trigger);
      log.info("---------------------------------------------------- Scheduler został dodany: {}", schedulerEnum.getTriggerKod());
    } catch (ObjectAlreadyExistsException ex) {
      log.info(
          "!!!  Pominięto dodawanie triggera - obiekt już istnieje: {}", schedulerEnum.getTriggerKod());
    } catch (SchedulerException ex) {
      log.error("Błąd planowania zadań", ex);
    }
  }

  public void stworzZadanieSchedulera(
      SchedulerSingleEnum schedulerEnum) {
    try {
      UUID uuid = UUID.randomUUID();
      JobDataMap jobData = new JobDataMap();
      JobDetail jobDetail = stworzJobDetail(schedulerEnum,uuid);
      Trigger trigger = stworzSimpleTrigger(schedulerEnum, jobDetail, jobData, uuid);
      scheduler.scheduleJob(jobDetail, trigger);
      log.info("---------------------------------------------------- Scheduler został dodany: {}", schedulerEnum.getTriggerKod());
    } catch (ObjectAlreadyExistsException ex) {
      log.info(
          "!!!  Pominięto dodawanie triggera - obiekt już istnieje: {}", schedulerEnum.getTriggerKod());
    } catch (SchedulerException ex) {
      log.error("Błąd planowania zadań", ex);
    }
  }

  public void stworzZadanieScheduleraRaportu(
      SchedulerSingleEnum schedulerEnum, Map<?,?> map) {
    try {
      UUID uuid = UUID.randomUUID();
      JobDataMap jobData = new JobDataMap(map);
      JobDetail jobDetail = stworzJobDetail(schedulerEnum,uuid);
      JobKey jobKey = JobKey.jobKey(schedulerEnum.getJobKod() + "_" + uuid);
      scheduler.addJob(jobDetail, false);
      scheduler.triggerJob(jobKey, jobData);
      log.info("---------------------------------------------------- Scheduler został dodany: {}", schedulerEnum.getTriggerKod());
    } catch (ObjectAlreadyExistsException ex) {
      log.info(
          "!!!  Pominięto dodawanie triggera - obiekt już istnieje: {}", schedulerEnum.getTriggerKod());
    } catch (SchedulerException ex) {
      log.error("!!!  Błąd planowania zadań", ex);
    }
  }

  public void usunZadanie(SchedulerSingleEnum schedulerEnum)
  {
    try {
      scheduler.deleteJob(new JobKey(schedulerEnum.getJobKod()));
    } catch (Exception ex) {
      log.error("Błąd usuwania zaplanowanego zadania", ex);
    }
  }

  private Trigger stworzCronTrigger(SchedulerEnum schedulerEnum, JobDetail jobDetail) {
    return quartzBuilderService.buildCronJobTrigger(
        jobDetail,
        CronTriggerSzczegoly.builder()
            .identyfikator(schedulerEnum.getTriggerKod())
            .wyrazenieCron(schedulerEnum.getCron())
            .build());
  }

  private Trigger stworzSimpleTrigger(
      SchedulerSingleEnum schedulerEnum, JobDetail jobDetail, JobDataMap jobData, UUID uuid) {
    return quartzBuilderService.buildSimpleJobTrigger(
        jobDetail,
        jobData,
        CronTriggerSzczegoly.builder()
            .identyfikator(schedulerEnum.getTriggerKod() + "_" + uuid)
            .build()
    );
  }

  private Trigger stworzDateTrigger(
      SchedulerSingleEnum schedulerEnum, JobDetail jobDetail, JobDataMap jobData, Long minutes, UUID uuid) {
    return quartzBuilderService.buildDateTimeJobTrigger(
        jobDetail,
        jobData,
        CronTriggerSzczegoly.builder()
            .identyfikator(schedulerEnum.getTriggerKod() + "_" + uuid)
            .build(),
        minutes);
  }

  private JobDetail stworzJobDetail(SchedulerEnum schedulerEnum) {
    return quartzBuilderService.buildJobDetail(
        JobSzczegoly.builder()
            .identyfikator(schedulerEnum.getJobKod())
            .klasaZadania(schedulerEnum.getKlasa())
            .build());
  }

  private JobDetail stworzJobDetail(SchedulerSingleEnum schedulerEnum, UUID uuid) {
    return quartzBuilderService.buildJobDetail(
        JobSzczegoly.builder()
            .identyfikator(schedulerEnum.getJobKod() + "_" + uuid)
            .klasaZadania(schedulerEnum.getKlasa())
            .build());
  }

}
