package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.KrsService;
import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezkrs.domain.ListaZaktualizowanychKrsFacade;
import com.baza.firmy.danezkrs.query.ListaZaktualizowanychKrsQueryFacade;
import com.baza.firmy.dto.ListaZmienionychWpisowDto;
import com.baza.firmy.podmiotygospodarcze.domain.PodmiotyGospodarczeFacade;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PobierajDaneZaktualizowanychFirmZKrsService implements BazowySchedulerService {

  private final ListaZaktualizowanychKrsQueryFacade listaZaktualizowanychKrsQueryFacade;
  private final ListaZaktualizowanychKrsFacade listaZaktualizowanychKrsFacade;
  private final PodmiotyGospodarczeFacade podmiotyGospodarczeFacade;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
  private final KrsService krsService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    if (listaZaktualizowanychKrsQueryFacade.czyIstniejeListaZaktualizowanychWpisowWTrakciePobierania()) {
      log.info("Zadanie PobierajDaneZaktualizowanychFirmZKrsService nie zostanie wykonane, ponieważ istnieje już lista zaktualizowanych wpisów w trakcie pobierania.");
      return;
    }
    log.info("Rozpoczynam pobieranie danych z KRS dla zaktualizowanych firm.");
    ListaZmienionychWpisowDto listaZmienionychWpisowDto =
        listaZaktualizowanychKrsQueryFacade.pobierzNieobsluzanaListeZaktualizowanychWpisow();
    if (listaZmienionychWpisowDto.getIdentyfikatoryWpisow().isEmpty()) {
      log.info("Brak zaktualizowanych wpisów do pobrania z KRS.");
      return;
    }
    List<String> listaNiepobranychKrs = new ArrayList<>();

    listaZaktualizowanychKrsFacade.zmienStatusListyWpisow(listaZmienionychWpisowDto.getUuid(), StatusPobieraniaEnum.W_TRAKCIE);
    listaZmienionychWpisowDto.getIdentyfikatoryWpisow()
       .forEach(krs -> {
         try {
           final var odpis = krsService.pobierzOdpisAktualny(krs);
           if (podmiotyGospodarczeQueryFacade.czyIstniejePoKrs(krs)) {
             podmiotyGospodarczeFacade.zaktualizujPodmiotGospodarczy(odpis.get());
           } else {
             podmiotyGospodarczeFacade.stworzPodmiotGospodarczy(odpis.get());
           }
         } catch (Exception e) {
           log.error("Błąd podczas pobierania danych z KRS dla zaktualizowanych firm: {}", e.getMessage(), e);
           listaNiepobranychKrs.add(krs);
         }
       });

    if (!listaNiepobranychKrs.isEmpty()) {
      log.warn("Nie udało się pobrać danych z KRS dla następujących wpisów: {}", listaNiepobranychKrs);
      listaZaktualizowanychKrsFacade.zmienStatusListyWpisow(listaZmienionychWpisowDto.getUuid(), StatusPobieraniaEnum.ZAKONCZONE_Z_BLENDAMI, listaNiepobranychKrs);
    } else {
      log.info("Pobieranie danych z KRS dla zaktualizowanych firm zakończone pomyślnie.");
      listaZaktualizowanychKrsFacade.zmienStatusListyWpisow(listaZmienionychWpisowDto.getUuid(), StatusPobieraniaEnum.ZAKONCZONE);
    }
  }
}
