package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.query.AdresQueryFacade;
import com.baza.firmy.constants.enums.BusinessStatus;
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

    if (Objects.nonNull(dzial1.siedzibaIAdres()) &&
        Objects.equals(dzial1.siedzibaIAdres().siedziba(), dzial1.siedzibaIAdres().adres())) {
      UUID adresUuid = spolkaService.zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresKorespondencyjnyUuid = adresUuid;
      adresDzialalnosciUuid = adresUuid;
    } else {
      adresKorespondencyjnyUuid = spolkaService.zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresDzialalnosciUuid = spolkaService.zaktualizujAdresDzialalnoszci(dzial1.siedzibaIAdres());
    }

    UUID pkdGlownyUuid = spolkaService.zaktualizujPkdGlowny(dzial3);
    List<UUID> pozostalePkdUuidList = spolkaService.zaktualizujPkdDodatkowe(dzial3);

    PodmiotGospodarczyEntity podmiotGospodarczyEntity = podmiotyGospodarczeMapper.toPodmiotGospodarczyEntity(odpisAktualnyResponse);
    podmiotGospodarczyEntity.setStatus(BusinessStatus.AKTYWNY);

    spolkaService.ustawWspolnikow(odpisAktualnyResponse, podmiotGospodarczyEntity);

    spolkaService.ustawReprezentacje(odpisAktualnyResponse, podmiotGospodarczyEntity);

    if (adresDzialalnosciUuid != null) {
      podmiotGospodarczyEntity.setAdresDzialalnosci(adresQueryFacade.getAdresPoUuid(adresDzialalnosciUuid));
    }

    if (adresKorespondencyjnyUuid != null) {
      podmiotGospodarczyEntity.setAdresKorespondencyjny(adresQueryFacade.getAdresPoUuid(adresKorespondencyjnyUuid));
    }

    if (pkdGlownyUuid != null) {
      podmiotGospodarczyEntity.setPkdGlowny(pkdQueryFasade.findByUuid(pkdGlownyUuid));
    }

    if (!pozostalePkdUuidList.isEmpty()) {
      podmiotGospodarczyEntity.setPkd(pkdQueryFasade.findByUuidList(pozostalePkdUuidList));
    }

    if (podmiotGospodarczyEntity.getRokPkd() == null) {
      podmiotGospodarczyEntity.setRokPkd(podmiotGospodarczyEntity.getDataRozpoczecia().isBefore(LocalDate.of(2025, 01, 01)) ? "2007" : "2025");
    }

    return podmiotyGospodarczeRepository.save(podmiotGospodarczyEntity).getUuid();
  }
}
