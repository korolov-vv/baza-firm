package com.baza.firmy.jdg.domain;

import com.baza.firmy.adresy.domain.AdresFacade;
import com.baza.firmy.adresy.query.AdresQueryFacade;
import com.baza.firmy.jdg.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.osoby.domain.OsobaFacade;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import com.baza.firmy.osoby.query.OsobaQueryFacade;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.domain.PkdFasade;
import com.baza.firmy.pkd.domain.dto.PkdDto;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.pkd.query.PkdViewEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StworzJdgUseCase {

  private final OsobaFacade osobaFacade;
  private final OsobaQueryFacade osobaQueryFacade;
  private final AdresFacade adresFacade;
  private final AdresQueryFacade adresQueryFacade;
  private final PkdFasade pkdFasade;
  private final PkdQueryFasade pkdQueryFasade;
  private final JdgRepository jdgRepository;
  private final JdgMapper jdgMapper;

  public UUID stworzJdg(JdgSzczegolyDto jdgSzczegolyDto) {
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

    JdgEntity jdgEntity = jdgMapper.toJdgEntity(jdgSzczegolyDto);

    if (adresDzialalnosciUuid != null) {
      jdgEntity.setAdresDzialalnosci(adresQueryFacade.getAdresPoUuid(adresDzialalnosciUuid));
    }

    if (adresKorespondencyjnyUuid != null) {
      jdgEntity.setAdresKorespondencyjny(adresQueryFacade.getAdresPoUuid(adresKorespondencyjnyUuid));
    }

    if (wlascicielUuid != null) {
      jdgEntity.setWlasciciel(osobaQueryFacade.findByUuid(wlascicielUuid));
    }

    if (pkdGlownyUuid != null) {
      jdgEntity.setPkdGlowny(pkdQueryFasade.findByUuid(pkdGlownyUuid));
    }

    if (!pozostalePkdUuidList.isEmpty()) {
      jdgEntity.setPkd(pkdQueryFasade.findByUuidList(pozostalePkdUuidList));
    }

    return jdgRepository.save(jdgEntity).getUuid();
  }

  private UUID zaktualizujWlasciciela(JdgSzczegolyDto jdgSzczegolyDto) {
    return osobaQueryFacade.findByNip(jdgSzczegolyDto.getWlasciciel().getNip())
        .map(OsobaViewEntity::getUuid)
        .orElseGet(() -> {
          StworzWlascicielaDto osobaDto = jdgSzczegolyDto.getWlasciciel();
          osobaDto.setObywatelstwa(jdgSzczegolyDto.getObywatelstwa());
          return osobaFacade.stworzOsobe(osobaDto);
        });
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
