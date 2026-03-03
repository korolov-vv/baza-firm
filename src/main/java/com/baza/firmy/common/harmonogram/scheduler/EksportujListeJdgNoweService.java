package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
class EksportujListeJdgNoweService implements BazowySchedulerService {

 private final QuartzManager quartzManager;

 @Override
 public void executeScheduler(JobExecutionContext jobExecutionContext) {
  log.info("Start EKSPORTUJ_LISTE_JDG_NOWE_JOB");
  Map<String, Object> parametry = Map.of(
      "parametryWyszukawania", ParametryWyszukiwaniaDto.builder()
              .pkd("4321Z,2712Z,6110Z")
              .dataRozpoczeciaOd(LocalDate.now().minusDays(6))
              .dataRozpoczeciaDo(LocalDate.now().minusDays(3))
          .build()
  );
  quartzManager.stworzZadanieScheduleraRaportu(
      SchedulerSingleEnum.SCHRACK_LISTA_FIRM_SCHEDULER, parametry);
  log.info("Complete EKSPORTUJ_LISTE_JDG_NOWE_JOB");

 }
}
