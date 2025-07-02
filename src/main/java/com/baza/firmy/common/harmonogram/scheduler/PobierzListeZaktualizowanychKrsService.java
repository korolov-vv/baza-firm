package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.PobierzDaneZCeidgService;
import com.baza.firmy.danezkrs.domain.ListaZaktualizowanychKrsFacade;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PobierzListeZaktualizowanychKrsService implements BazowySchedulerService {

  private final ListaZaktualizowanychKrsFacade listaZaktualizowanychKrsFacade;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    listaZaktualizowanychKrsFacade.pobierzOrazZapiszListeZaktualizowanychWpisow(LocalDate.now().minusDays(3), 0, 23);
  }
}

