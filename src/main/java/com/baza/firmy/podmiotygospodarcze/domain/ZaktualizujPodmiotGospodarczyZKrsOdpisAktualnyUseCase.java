package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.domain.AdresFacade;
import com.baza.firmy.adresy.query.AdresQueryFacade;
import com.baza.firmy.osoby.domain.dto.ReprezentacjaDto;
import com.baza.firmy.osoby.domain.dto.ReprezentantDto;
import com.baza.firmy.osoby.domain.dto.WlascicielDto;
import com.baza.firmy.pkd.domain.PkdFasade;
import com.baza.firmy.pkd.domain.dto.PkdDto;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.pkd.query.PkdViewEntity;
import com.baza.firmy.response.krs.CzlonekZarzaduResponseResponse;
import com.baza.firmy.response.krs.Dzial3Response;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import com.baza.firmy.response.krs.SiedzibaIAdresResponse;
import com.baza.firmy.response.krs.WspolnikSpzooResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class ZaktualizujPodmiotGospodarczyZKrsOdpisAktualnyUseCase {

  private final AdresQueryFacade adresQueryFacade;
  private final PkdQueryFasade pkdQueryFasade;
  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;
  private final PodmiotyGospodarczeMapper podmiotyGospodarczeMapper;
  private final PodmiotGospodarczyService podmiotGospodarczyService;

  @Transactional
  public UUID zaktualizujPodmiotGospodarczy(OdpisAktualnyResponse odpisAktualnyResponse) {
    UUID adresKorespondencyjnyUuid;
    UUID adresDzialalnosciUuid;

    final var dzial1 = odpisAktualnyResponse.odpis().dane().dzial1();
    final var dzial3 = odpisAktualnyResponse.odpis().dane().dzial3();

    if (Objects.equals(dzial1.siedzibaIAdres().siedziba(), dzial1.siedzibaIAdres().adres())) {
      UUID adresUuid = podmiotGospodarczyService.zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresKorespondencyjnyUuid = adresUuid;
      adresDzialalnosciUuid = adresUuid;
    } else {
      adresKorespondencyjnyUuid = podmiotGospodarczyService.zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresDzialalnosciUuid = podmiotGospodarczyService.zaktualizujAdresDzialalnoszci(dzial1.siedzibaIAdres());
    }

    UUID pkdGlownyUuid = podmiotGospodarczyService.zaktualizujPkdGlowny(dzial3);
    List<UUID> pozostalePkdUuidList = podmiotGospodarczyService.zaktualizujPkdDodatkowe(dzial3);

    PodmiotGospodarczeEntity podmiotGospodarczeEntity = podmiotyGospodarczeRepository.findByNumerKrs(odpisAktualnyResponse.odpis().naglowekA().numerKRS())
        .orElseThrow(() -> new IllegalArgumentException("Podmiot gospodarczy o podanym KRS nie istnieje"));

    podmiotyGospodarczeMapper.toJdgEntity(podmiotGospodarczeEntity, odpisAktualnyResponse);

    podmiotGospodarczyService.ustawWspolnikow(odpisAktualnyResponse, podmiotGospodarczeEntity);

    podmiotGospodarczyService.ustawReprezentacje(odpisAktualnyResponse, podmiotGospodarczeEntity);

    if (adresDzialalnosciUuid != null) {
      podmiotGospodarczeEntity.setAdresDzialalnosci(adresQueryFacade.getAdresPoUuid(adresDzialalnosciUuid));
    }

    if (adresKorespondencyjnyUuid != null) {
      podmiotGospodarczeEntity.setAdresKorespondencyjny(adresQueryFacade.getAdresPoUuid(adresKorespondencyjnyUuid));
    }

    if (pkdGlownyUuid != null) {
      podmiotGospodarczeEntity.setPkdGlowny(pkdQueryFasade.findByUuid(pkdGlownyUuid));
    }

    if (!pozostalePkdUuidList.isEmpty()) {
      podmiotGospodarczeEntity.setPkd(pkdQueryFasade.findByUuidList(pozostalePkdUuidList));
    }

    if (podmiotGospodarczeEntity.getRokPkd() == null) {
      podmiotGospodarczeEntity.setRokPkd(podmiotGospodarczeEntity.getDataRozpoczecia().isBefore(LocalDate.of(2025, 01, 01)) ? "2007" : "2025");
    }

    return podmiotyGospodarczeRepository.save(podmiotGospodarczeEntity).getUuid();
  }
}
