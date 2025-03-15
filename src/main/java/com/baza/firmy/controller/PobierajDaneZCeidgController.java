package com.baza.firmy.controller;

import com.baza.firmy.common.harmonogram.scheduler.QuartzManager;
import com.baza.firmy.common.harmonogram.scheduler.SchedulerSingleEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/dane-ceidg")
@Tag (name = "JDG API", description = "Dostęp do JDG")
class PobierajDaneZCeidgController {

  private final QuartzManager quartzManager;

  @PostMapping ("/pobierz-listy-aktywnych-jdg-wstecz")
  public void pobierajListyAktywnychJdgWsteczIZapisz() {
    quartzManager.stworzZadanieSchedulera(SchedulerSingleEnum.POBIERAJ_LISTE_JDG_SCHEDULER);
  }

  @PostMapping ("/pobierz-szczegoly-jdg")
  public void pobierajSzczegolyJdg() {
    quartzManager.stworzZadanieSchedulera(SchedulerSingleEnum.POBIERAJ_SZCZEGOLY_JDG_SCHEDULER);
  }
}
