package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodmiotyGospodarczeFacade {

  private final StworzPodmiotGospodarczyZJdgUseCase stworzPodmiotGospodarczyZJdgUseCase;
  private final StworzPodmiotGospodarczyZKrsOdpisAktualnyUseCase stworzPodmiotGospodarczyZKrsOdpisAktualnyUseCase;
  private final ZaktualizujPodmiotGospodarczyZKrsOdpisAktualnyUseCase zaktualizujPodmiotGospodarczyZKrsOdpisAktualnyUseCase;
  private final ZaktualizujPodmiotGospodarczyZJdgUseCase zaktualizujPodmiotGospodarczyZJdgUseCase;
  private final ZaktualizujDaneKontaktoweUseCase zaktualizujDaneKontaktoweUseCase;

  public UUID stworzPodmiotGospodarczy(JdgSzczegolyDto jdgSzczegolyDto) {
    UUID savedUuid = stworzPodmiotGospodarczyZJdgUseCase.stworzPodmiotGospodarczy(jdgSzczegolyDto);
    log.info("Zapisano Podmiot Gospodarczy: UUID = {}", savedUuid);
    return savedUuid;
  }

  public UUID zaktualizujPodmiotGospodarczy(JdgSzczegolyDto jdgSzczegolyDto) {
    UUID savedUuid = zaktualizujPodmiotGospodarczyZJdgUseCase.zaktualizujPodmiotGospodarczy(jdgSzczegolyDto);
    log.info("Zaktualizowano Podmiot Gospodarczy: UUID = {}", savedUuid);
    return savedUuid;
  }

  public UUID stworzPodmiotGospodarczy(OdpisAktualnyResponse odpisAktualnyResponse) {
    UUID savedUuid = stworzPodmiotGospodarczyZKrsOdpisAktualnyUseCase.stworzPodmiotGospodarczy(odpisAktualnyResponse);
    log.info("Zapisano Podmiot Gospodarczy: UUID = {}", savedUuid);
    return savedUuid;
  }

  public void zaktualizujPodmiotGospodarczy(OdpisAktualnyResponse odpis) {
    UUID savedUuid = zaktualizujPodmiotGospodarczyZKrsOdpisAktualnyUseCase.zaktualizujPodmiotGospodarczy(odpis);
    log.info("Zaktualizowano Podmiot Gospodarczy: UUID = {}", savedUuid);
  }

  public void zaktualizujDaneKontaktowe(FirmaPortalZewnDto firma) {
    zaktualizujDaneKontaktoweUseCase.zaktualizujDaneKontaktowe(firma);
  }
}
