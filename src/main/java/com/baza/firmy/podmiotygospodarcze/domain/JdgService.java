package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.domain.AdresFacade;
import com.baza.firmy.osoby.domain.OsobaFacade;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import com.baza.firmy.osoby.query.OsobaQueryFacade;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.domain.PkdFasade;
import com.baza.firmy.pkd.domain.dto.PkdDto;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.pkd.query.PkdViewEntity;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
class JdgService {

  private final OsobaFacade osobaFacade;
  private final OsobaQueryFacade osobaQueryFacade;
  private final AdresFacade adresFacade;
  private final PkdFasade pkdFasade;
  private final PkdQueryFasade pkdQueryFasade;

  UUID zaktualizujWlasciciela(JdgSzczegolyDto jdgSzczegolyDto) {
    if (jdgSzczegolyDto.getWlasciciel().getNip() == null) {
      return stworzOsobe(jdgSzczegolyDto);
    }

    return osobaQueryFacade.findByNip(jdgSzczegolyDto.getWlasciciel().getNip())
        .map(OsobaViewEntity::getUuid)
        .orElseGet(() -> stworzOsobe(jdgSzczegolyDto));
  }

  UUID zaktualizujAdresDzialalnoszci(JdgSzczegolyDto jdgSzczegolyDto) {
    return adresFacade.stworzAdres(jdgSzczegolyDto.getAdresDzialalnosci());
  }

  UUID zaktualizujAdresKorespondencyjny(JdgSzczegolyDto jdgSzczegolyDto) {
    return adresFacade.stworzAdres(jdgSzczegolyDto.getAdresKorespondencyjny());
  }

  UUID zaktualizujPkdGlowny(JdgSzczegolyDto jdgSzczegolyDto) {
    if (jdgSzczegolyDto.getPkdGlowny().isEmpty() || jdgSzczegolyDto.getPkdGlowny().get().getKod().isBlank()) {
      return null;
    }

    return zaktualizujPkd(jdgSzczegolyDto.getPkdGlowny().get());
  }

  List<UUID> zaktualizujPkdDodatkowe(JdgSzczegolyDto jdgSzczegolyDto) {
    if (jdgSzczegolyDto.getPkd() == null || jdgSzczegolyDto.getPkd().isEmpty()) {
      return Collections.emptyList();
    }

    Set<UUID> listaUuidKodowPkdZapisanych = new HashSet<>();
    jdgSzczegolyDto.getPkd().stream()
        .filter(pkd -> pkd != null && !pkd.equals(jdgSzczegolyDto.getPkdGlowny().orElse(null)))
        .forEach(kod -> listaUuidKodowPkdZapisanych.add(zaktualizujPkd(kod)));
    return List.copyOf(listaUuidKodowPkdZapisanych);
  }

  private UUID stworzOsobe(JdgSzczegolyDto jdgSzczegolyDto) {
    StworzWlascicielaDto osobaDto = jdgSzczegolyDto.getWlasciciel();
    osobaDto.setObywatelstwa(jdgSzczegolyDto.getObywatelstwa());
    return osobaFacade.stworzOsobe(osobaDto);
  }

  private UUID zaktualizujPkd(Pkd pkd) {
    return pkdQueryFasade.findByKod(pkd.getKod().trim())
        .map(PkdViewEntity::getUuid)
        .orElseGet(() -> pkdFasade.stworzPkd(PkdDto.builder()
            .uuid(UUID.randomUUID())
            .kod(pkd.getKod().trim())
            .nazwa(pkd.getNazwa().trim())
            .build()));
  }
}
