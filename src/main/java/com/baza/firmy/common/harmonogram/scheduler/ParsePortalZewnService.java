package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.PortalZewnParser;
import com.baza.firmy.danezportaluzewn.query.DaneZPortaluZewnQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParsePortalZewnService implements BazowySchedulerService {

  private final PortalZewnParser portalZewnParser;
  private final DaneZPortaluZewnQueryFacade daneZPortaluZewnQueryFacade;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
      if (daneZPortaluZewnQueryFacade.czyIstniejeKategoriaWTrakciePobierania()) {
          log.info("Zadanie ParsePortalZewnService nie zostanie wykonane, ponieważ istnieje już Kategoria w trakcie pobierania.");
          return;
      }

      portalZewnParser.parse();
  }
}
