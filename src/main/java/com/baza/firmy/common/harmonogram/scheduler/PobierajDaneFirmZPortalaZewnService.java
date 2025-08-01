package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.common.service.KrsService;
import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.DaneZPortaluZewnFacade;
import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import com.baza.firmy.danezportaluzewn.query.DaneZPortaluZewnQueryFacade;
import com.baza.firmy.podmiotygospodarcze.domain.PodmiotyGospodarczeFacade;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PobierajDaneFirmZPortalaZewnService implements BazowySchedulerService {

  private final DaneZPortaluZewnQueryFacade daneZPortaluZewnQueryFacade;
  private final DaneZPortaluZewnFacade daneZPortaluZewnFacade;
  private final PodmiotyGospodarczeFacade podmiotyGospodarczeFacade;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
  private final KrsService krsService;

  @Override
  public void executeScheduler(JobExecutionContext jobExecutionContext) {
    if (daneZPortaluZewnQueryFacade.czyIstniejaWpisyWTrakciePobierania()) {
      log.info("Zadanie PobierajDaneFirmZPortalaZewnService nie zostanie wykonane, ponieważ istnieje wpis w trakcie pobierania.");
      return;
    }

    DaneZPortaluZewnDto daneZPortaluZewnDto =
        daneZPortaluZewnQueryFacade.pobierzNiepodjetaListeWpisow();

    if (Objects.isNull(daneZPortaluZewnDto) || daneZPortaluZewnDto.getFirmy().isEmpty()) {
      log.info("Brak wpisów z portalu zewnętrznego do pobrania z KRS.");
      return;
    }

    log.info("Rozpoczynam pobieranie danych z KRS dla firm z portalu zewnętrznego.");
    List<FirmaPortalZewnDto> listaNiepobranychFirm = new ArrayList<>();

    daneZPortaluZewnFacade.zmienStatusDanychZPortaluZewn(
        daneZPortaluZewnDto.getUuid(), StatusPobieraniaEnum.W_TRAKCIE);
    daneZPortaluZewnDto.getFirmy()
       .forEach(firma -> {
         if (firma.getKrs().isPresent()) {
           try{
             pobierzDaneZKrs(firma, listaNiepobranychFirm);
           } catch (Exception e) {
             log.error("Błąd podczas pobierania danych z KRS dla firmy: {}", firma.getNazwa(), e);
             listaNiepobranychFirm.add(firma);
           }
         } else {
           try {
             zaktualizujDaneKontaktowe(firma);
           } catch (Exception e) {
             log.error("Błąd podczas aktualizacji danych kontaktowych dla firmy: {}", firma.getNazwa(), e);
           }
         }
       });

    if (!listaNiepobranychFirm.isEmpty()) {
      log.warn("Nie udało się pobrać danych z KRS dla następujących wpisów: {}", listaNiepobranychFirm);
      daneZPortaluZewnFacade.zmienStatusDanychZPortaluZewn(
          daneZPortaluZewnDto.getUuid(), StatusPobieraniaEnum.ZAKONCZONE_Z_BLENDAMI, listaNiepobranychFirm);
    } else {
      log.info("Pobieranie danych z KRS dla zaktualizowanych firm zakończone pomyślnie.");
      daneZPortaluZewnFacade.zmienStatusDanychZPortaluZewn(
          daneZPortaluZewnDto.getUuid(), StatusPobieraniaEnum.ZAKONCZONE);
    }
  }

  private void pobierzDaneZKrs(FirmaPortalZewnDto firma, List<FirmaPortalZewnDto> listaNiepobranychFirm)
      throws ExecutionException, InterruptedException {
    final String krs = firma.getKrs().get();
      if (podmiotyGospodarczeQueryFacade.czyIstniejePoKrs(krs)) {
        zaktualizujDaneKontaktowe(firma);
      } else {
        final var odpis = krsService.pobierzOdpisAktualny(krs);
        if (Objects.isNull(odpis.get())) {
          log.warn("Nie udało się pobrać odpisu aktualnego KRS dla numeru: {}", krs);
          listaNiepobranychFirm.add(firma);
          return;
        }
        podmiotyGospodarczeFacade.stworzPodmiotGospodarczy(odpis.get());
      }
  }

  private void zaktualizujDaneKontaktowe(FirmaPortalZewnDto firma) {
    if (czyWpisZawieraDaneKontaktowe(firma)) {
      podmiotyGospodarczeFacade.zaktualizujDaneKontaktowe(firma);
    }
  }

  private boolean czyWpisZawieraDaneKontaktowe(FirmaPortalZewnDto firma) {
    return firma.getEmail().isPresent() || firma.getTelefon().isPresent() || firma.getStronaWww().isPresent();
  }
}
