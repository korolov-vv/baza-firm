package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.domain.AdresFacade;
import com.baza.firmy.adresy.query.AdresQueryFacade;
import com.baza.firmy.osoby.domain.OsobaFacade;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import com.baza.firmy.osoby.query.OsobaQueryFacade;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.domain.PkdFasade;
import com.baza.firmy.pkd.domain.dto.PkdDto;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.pkd.query.PkdViewEntity;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class StworzPodmiotGospodarczyZJdgUseCase {

  private final OsobaFacade osobaFacade;
  private final OsobaQueryFacade osobaQueryFacade;
  private final AdresFacade adresFacade;
  private final AdresQueryFacade adresQueryFacade;
  private final PkdFasade pkdFasade;
  private final PkdQueryFasade pkdQueryFasade;
  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;
  private final PodmiotyGospodarczeMapper podmiotyGospodarczeMapper;

  public UUID stworzPodmiotGospodarczy(JdgSzczegolyDto jdgSzczegolyDto) {
    UUID adresKorespondencyjnyUuid;
    UUID adresDzialalnosciUuid;
    if (Objects.equals(jdgSzczegolyDto.getAdresDzialalnosci(), jdgSzczegolyDto.getAdresKorespondencyjny())) {
      UUID adresUuid = zaktualizujAdresKorespondencyjny(jdgSzczegolyDto);
      adresKorespondencyjnyUuid = adresUuid;
      adresDzialalnosciUuid = adresUuid;
    } else {
      adresKorespondencyjnyUuid = zaktualizujAdresKorespondencyjny(jdgSzczegolyDto);
      adresDzialalnosciUuid = zaktualizujAdresDzialalnoszci(jdgSzczegolyDto);
    }
    UUID wlascicielUuid = zaktualizujWlasciciela(jdgSzczegolyDto);
    UUID pkdGlownyUuid = zaktualizujPkdGlowny(jdgSzczegolyDto);
    List<UUID> pozostalePkdUuidList = zaktualizujPkdDodatkowe(jdgSzczegolyDto);

    PodmiotyGospodarczeEntity podmiotyGospodarczeEntity = podmiotyGospodarczeMapper.toJdgEntity(jdgSzczegolyDto);

    if (adresDzialalnosciUuid != null) {
      podmiotyGospodarczeEntity.setAdresDzialalnosci(adresQueryFacade.getAdresPoUuid(adresDzialalnosciUuid));
    }

    if (adresKorespondencyjnyUuid != null) {
      podmiotyGospodarczeEntity.setAdresKorespondencyjny(adresQueryFacade.getAdresPoUuid(adresKorespondencyjnyUuid));
    }

    if (wlascicielUuid != null) {
      podmiotyGospodarczeEntity.setWlasciciel(osobaQueryFacade.findByUuid(wlascicielUuid));
    }

    if (pkdGlownyUuid != null) {
      podmiotyGospodarczeEntity.setPkdGlowny(pkdQueryFasade.findByUuid(pkdGlownyUuid));
    }

    if (!pozostalePkdUuidList.isEmpty()) {
      podmiotyGospodarczeEntity.setPkd(pkdQueryFasade.findByUuidList(pozostalePkdUuidList));
    }

    if (podmiotyGospodarczeEntity.getRokPkd() == null) {
      podmiotyGospodarczeEntity.setRokPkd(podmiotyGospodarczeEntity.getDataRozpoczecia().isBefore(LocalDate.of(2025, 01, 01)) ? "2007" : "2025");
    }

    return podmiotyGospodarczeRepository.save(podmiotyGospodarczeEntity).getUuid();
}

private UUID zaktualizujWlasciciela(JdgSzczegolyDto jdgSzczegolyDto) {
    if (jdgSzczegolyDto.getWlasciciel().getNip() == null) {
      return stworzOsobe(jdgSzczegolyDto);
    }

    return osobaQueryFacade.findByNip(jdgSzczegolyDto.getWlasciciel().getNip())
      .map(OsobaViewEntity::getUuid)
      .orElseGet(() -> stworzOsobe(jdgSzczegolyDto));
  }

  private UUID stworzOsobe(JdgSzczegolyDto jdgSzczegolyDto) {
    StworzWlascicielaDto osobaDto = jdgSzczegolyDto.getWlasciciel();
    osobaDto.setObywatelstwa(jdgSzczegolyDto.getObywatelstwa());
    return osobaFacade.stworzOsobe(osobaDto);
  }

  private UUID zaktualizujAdresDzialalnoszci(JdgSzczegolyDto jdgSzczegolyDto) {
    return adresFacade.stworzAdres(jdgSzczegolyDto.getAdresDzialalnosci());
  }
  private UUID zaktualizujAdresKorespondencyjny(JdgSzczegolyDto jdgSzczegolyDto) {
    return adresFacade.stworzAdres(jdgSzczegolyDto.getAdresKorespondencyjny());
  }

  private UUID zaktualizujPkdGlowny(JdgSzczegolyDto jdgSzczegolyDto) {
    if (jdgSzczegolyDto.getPkdGlowny().isEmpty() || jdgSzczegolyDto.getPkdGlowny().get().isBlank()) {
      return null;
    }

    return zaktualizujPkd(jdgSzczegolyDto.getPkdGlowny().get());
  }

  private List<UUID> zaktualizujPkdDodatkowe(JdgSzczegolyDto jdgSzczegolyDto) {
    if (jdgSzczegolyDto.getPkd() == null || jdgSzczegolyDto.getPkd().isEmpty()) {
      return Collections.emptyList();
    }

    List<UUID> listaUuidKodowPkdZapisanych = new ArrayList<>();
    usunDuplikatyPkd(jdgSzczegolyDto.getPkd()).stream()
        .filter(pkd -> pkd != null && !pkd.equals(jdgSzczegolyDto.getPkdGlowny().orElse(null)))
        .forEach(kod -> listaUuidKodowPkdZapisanych.add(zaktualizujPkd(kod)));
    return listaUuidKodowPkdZapisanych;
  }

  private Set<String> usunDuplikatyPkd(List<String> kodyPkd) {
    Set<String> pkdUnikalne = new HashSet<>();
    kodyPkd.stream()
        .filter(n -> !pkdUnikalne.add(n))
        .toList();
    return pkdUnikalne;
  }

  private UUID zaktualizujPkd(String pkd) {
    return pkdQueryFasade.findByKod(pkd.trim())
        .map(PkdViewEntity::getUuid)
        .orElseGet(() -> pkdFasade.stworzPkd(PkdDto.builder()
            .uuid(UUID.randomUUID())
            .kod(pkd.trim())
            .build()));
  }
}
