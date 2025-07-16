package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.query.AdresQueryFacade;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class StworzPodmiotGospodarczyZKrsOdpisAktualnyUseCase {

  private final AdresQueryFacade adresQueryFacade;
  private final PkdQueryFasade pkdQueryFasade;
  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;
  private final PodmiotyGospodarczeMapper podmiotyGospodarczeMapper;
  private final SpolkaService spolkaService;

  @Transactional
  public UUID stworzPodmiotGospodarczy(OdpisAktualnyResponse odpisAktualnyResponse) {

    UUID adresKorespondencyjnyUuid;
    UUID adresDzialalnosciUuid;

    final var dzial1 = odpisAktualnyResponse.odpis().dane().dzial1();
    final var dzial3 = odpisAktualnyResponse.odpis().dane().dzial3();

    if (Objects.equals(dzial1.siedzibaIAdres().siedziba(), dzial1.siedzibaIAdres().adres())) {
      UUID adresUuid = spolkaService.zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresKorespondencyjnyUuid = adresUuid;
      adresDzialalnosciUuid = adresUuid;
    } else {
      adresKorespondencyjnyUuid = spolkaService.zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresDzialalnosciUuid = spolkaService.zaktualizujAdresDzialalnoszci(dzial1.siedzibaIAdres());
    }

    UUID pkdGlownyUuid = spolkaService.zaktualizujPkdGlowny(dzial3);
    List<UUID> pozostalePkdUuidList = spolkaService.zaktualizujPkdDodatkowe(dzial3);

    PodmiotGospodarczeEntity podmiotGospodarczeEntity = podmiotyGospodarczeMapper.toJdgEntity(odpisAktualnyResponse);

    spolkaService.ustawWspolnikow(odpisAktualnyResponse, podmiotGospodarczeEntity);

    spolkaService.ustawReprezentacje(odpisAktualnyResponse, podmiotGospodarczeEntity);

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
