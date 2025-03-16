package com.baza.firmy.schrack.controller;

import com.baza.firmy.common.harmonogram.scheduler.QuartzManager;
import com.baza.firmy.common.harmonogram.scheduler.SchedulerSingleEnum;
import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/schrack")
class SchrackController {

  private final QuartzManager quartzManager;

  @PostMapping ("/export")
  @Operation (summary = "Usługa eksportująca listę JDG do pliku XLSX")
  public ResponseEntity<Void> pobierzListeJdgDoXslx(@RequestBody ParametryWyszukiwaniaDto parametryWyszukiwania) {
    final Map<String, Object> parametry = Map.of(
        "parametryWyszukawania", parametryWyszukiwania
    );
    quartzManager.stworzZadanieScheduleraRaportu(
        SchedulerSingleEnum.SCHRACK_LISTA_FIRM_SCHEDULER, parametry);
    return ResponseEntity.ok().build();
  }
}
