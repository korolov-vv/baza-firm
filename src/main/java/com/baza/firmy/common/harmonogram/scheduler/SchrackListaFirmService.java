package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class SchrackListaFirmService implements BazowySchedulerService {

  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    ParametryWyszukiwaniaDto parametryWyszukiwaniaDto = (ParametryWyszukiwaniaDto) jobExecutionContext.getMergedJobDataMap().get("parametryWyszukawania");
    log.info("Rozpoczęcie eksportu danych: \n {} \ndo pliku", parametryWyszukiwaniaDto);
    podmiotyGospodarczeQueryFacade.exportujDoXlsx(parametryWyszukiwaniaDto);
    log.info("Zakończono eksport danych: \n {} \ndo pliku", parametryWyszukiwaniaDto);
  }
}

