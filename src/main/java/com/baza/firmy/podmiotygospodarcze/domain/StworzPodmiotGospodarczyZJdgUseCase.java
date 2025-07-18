package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.query.AdresQueryFacade;
import com.baza.firmy.osoby.query.OsobaQueryFacade;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
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
class StworzPodmiotGospodarczyZJdgUseCase {

  private final OsobaQueryFacade osobaQueryFacade;
  private final AdresQueryFacade adresQueryFacade;
  private final PkdQueryFasade pkdQueryFasade;
  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;
  private final PodmiotyGospodarczeMapper podmiotyGospodarczeMapper;
  private final JdgService jdgService;


  @Transactional
  public UUID stworzPodmiotGospodarczy(JdgSzczegolyDto jdgSzczegolyDto) {
    UUID adresKorespondencyjnyUuid;
    UUID adresDzialalnosciUuid;
    if (Objects.equals(jdgSzczegolyDto.getAdresDzialalnosci(), jdgSzczegolyDto.getAdresKorespondencyjny())) {
      UUID adresUuid = jdgService.zaktualizujAdresKorespondencyjny(jdgSzczegolyDto);
      adresKorespondencyjnyUuid = adresUuid;
      adresDzialalnosciUuid = adresUuid;
    } else {
      adresKorespondencyjnyUuid = jdgService.zaktualizujAdresKorespondencyjny(jdgSzczegolyDto);
      adresDzialalnosciUuid = jdgService.zaktualizujAdresDzialalnoszci(jdgSzczegolyDto);
    }
    UUID wlascicielUuid = jdgService.zaktualizujWlasciciela(jdgSzczegolyDto);
    UUID pkdGlownyUuid = jdgService.zaktualizujPkdGlowny(jdgSzczegolyDto);
    List<UUID> pozostalePkdUuidList = jdgService.zaktualizujPkdDodatkowe(jdgSzczegolyDto);

    PodmiotGospodarczeEntity podmiotGospodarczeEntity = podmiotyGospodarczeMapper.toPodmiotGospodarczyEntity(jdgSzczegolyDto);

    if (adresDzialalnosciUuid != null) {
      podmiotGospodarczeEntity.setAdresDzialalnosci(adresQueryFacade.getAdresPoUuid(adresDzialalnosciUuid));
    }

    if (adresKorespondencyjnyUuid != null) {
      podmiotGospodarczeEntity.setAdresKorespondencyjny(adresQueryFacade.getAdresPoUuid(adresKorespondencyjnyUuid));
    }

    if (wlascicielUuid != null) {
      podmiotGospodarczeEntity.setWlasciciel(osobaQueryFacade.findByUuid(wlascicielUuid));
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
