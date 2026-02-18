package com.baza.firmy.firmysubscrypcje.domain;

import com.baza.firmy.common.harmonogram.scheduler.QuartzManager;
import com.baza.firmy.common.harmonogram.scheduler.SchedulerSingleEnum;
import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class ZaktualizujStatusSubscrypcjiFirmyUseCase {

  private final FirmySubscrypcjeRepository firmySubscrypcjeRepository;
  private final QuartzManager quartzManager;

  public UUID zaktualizujStatusSubscrypcjiFirmy(UUID uuid, StatusSubscrypcji nowyStatus) {
    FirmaSubscrypcjaEntity firmaSubscrypcja = firmySubscrypcjeRepository.findByUuid(uuid)
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja firmy o UUID: " + uuid + " nie istnieje"));

    StatusSubscrypcji staryStatus = firmaSubscrypcja.getStatusSubscrypcji();
    firmaSubscrypcja.setStatusSubscrypcji(nowyStatus);

    FirmaSubscrypcjaEntity zapisanaSubscrypcjaFirmy = firmySubscrypcjeRepository.save(firmaSubscrypcja);

    // Trigger scheduler to create FirmaCrm list only when status changes to AKTYWNA
    if (staryStatus != StatusSubscrypcji.AKTYWNA && nowyStatus == StatusSubscrypcji.AKTYWNA) {
      quartzManager.stworzZadanieScheduleraRaportu(
              SchedulerSingleEnum.STWORZ_LISTE_FIRM_CRM_DLA_KLIENTA_SCHEDULER,
              Map.of("firmaSubscrypcjaUuid", zapisanaSubscrypcjaFirmy.getUuid()));
    }

    return zapisanaSubscrypcjaFirmy.getUuid();
  }
}

