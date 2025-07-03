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
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class StworzPodmiotGospodarczyZKrsOdpisAktualnyUseCase {

  private final AdresFacade adresFacade;
  private final AdresQueryFacade adresQueryFacade;
  private final PkdFasade pkdFasade;
  private final PkdQueryFasade pkdQueryFasade;
  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;
  private final PodmiotyGospodarczeMapper podmiotyGospodarczeMapper;

  @Transactional
  public UUID stworzPodmiotGospodarczy(OdpisAktualnyResponse odpisAktualnyResponse) {

    UUID adresKorespondencyjnyUuid;
    UUID adresDzialalnosciUuid;

    final var dzial1 = odpisAktualnyResponse.odpis().dane().dzial1();
    final var dzial3 = odpisAktualnyResponse.odpis().dane().dzial3();

    if (Objects.equals(dzial1.siedzibaIAdres().siedziba(), dzial1.siedzibaIAdres().adres())) {
      UUID adresUuid = zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresKorespondencyjnyUuid = adresUuid;
      adresDzialalnosciUuid = adresUuid;
    } else {
      adresKorespondencyjnyUuid = zaktualizujAdresKorespondencyjny(dzial1.siedzibaIAdres());
      adresDzialalnosciUuid = zaktualizujAdresDzialalnoszci(dzial1.siedzibaIAdres());
    }

    UUID pkdGlownyUuid = zaktualizujPkdGlowny(dzial3);
    List<UUID> pozostalePkdUuidList = zaktualizujPkdDodatkowe(dzial3);

    PodmiotGospodarczeEntity podmiotGospodarczeEntity = podmiotyGospodarczeMapper.toJdgEntity(odpisAktualnyResponse);

    ustawWspolnikow(odpisAktualnyResponse, podmiotGospodarczeEntity);

    ustawReprezentacje(odpisAktualnyResponse, podmiotGospodarczeEntity);

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

  private void ustawReprezentacje(OdpisAktualnyResponse odpisAktualnyResponse, PodmiotGospodarczeEntity podmiotGospodarczeEntity) {
    final var reprezentacja = odpisAktualnyResponse.odpis().dane().dzial2().reprezentacja();
    podmiotGospodarczeEntity.setReprezentacja(
        ReprezentacjaDto.builder()
            .nazwaOrganu(reprezentacja.nazwaOrganu())
            .sposobReprezentacji(reprezentacja.sposobReprezentacji())
            .sklad(reprezentacja.sklad().stream()
                .map(this::stworzReprezentanta)
                .toList())
            .build()
    );
  }

  private ReprezentantDto stworzReprezentanta(CzlonekZarzaduResponseResponse reprezentant) {
    return ReprezentantDto.builder()
        .funkcjaWOrganie(reprezentant.funkcjaWOrganie())
        .czyZawieszona(reprezentant.czyZawieszona())
        .imie(reprezentant.imiona().imie())
        .nazwisko(reprezentant.nazwisko().nazwiskoICzlon())
        .pesel(reprezentant.identyfikator().pesel())
        .nip(reprezentant.identyfikator().nip())
        .regon(reprezentant.identyfikator().regon())
        .build();
  }

  private void ustawWspolnikow(OdpisAktualnyResponse odpisAktualnyResponse, PodmiotGospodarczeEntity podmiotGospodarczeEntity) {
    podmiotGospodarczeEntity.getWspolnicySpzoo().clear();
    if (Objects.nonNull(odpisAktualnyResponse.odpis().dane().dzial1().wspolnicySpzoo())) {
      podmiotGospodarczeEntity.getWspolnicySpzoo().addAll(
          odpisAktualnyResponse.odpis().dane().dzial1().wspolnicySpzoo().stream()
              .map(this::stworzWlasciciela
              )
              .toList()
      );
    }
  }

  private WlascicielDto stworzWlasciciela(WspolnikSpzooResponse wspolnikSpzooResponse) {
    return WlascicielDto.builder()
        .nazwisko(Objects.nonNull(wspolnikSpzooResponse.nazwisko()) ? wspolnikSpzooResponse.nazwisko().nazwiskoICzlon() : Strings.EMPTY)
        .imie(Objects.nonNull(wspolnikSpzooResponse.imiona())? wspolnikSpzooResponse.imiona().imie() : Strings.EMPTY)
        .pesel(Objects.nonNull(wspolnikSpzooResponse.identyfikator()) ? wspolnikSpzooResponse.identyfikator().pesel() : Strings.EMPTY)
        .nip(Objects.nonNull(wspolnikSpzooResponse.identyfikator()) ? wspolnikSpzooResponse.identyfikator().nip() : Strings.EMPTY)
        .regon(Objects.nonNull(wspolnikSpzooResponse.identyfikator()) ? wspolnikSpzooResponse.identyfikator().regon() : Strings.EMPTY)
        .posiadaneUdzialy(wspolnikSpzooResponse.posiadaneUdzialy())
        .czyPosiadaCaloscUdzialow(wspolnikSpzooResponse.czyPosiadaCaloscUdzialow())
        .build();
  }

  private UUID zaktualizujAdresDzialalnoszci(SiedzibaIAdresResponse siedzibaIAdres) {
    return adresFacade.stworzAdres(siedzibaIAdres.adres());
  }
  private UUID zaktualizujAdresKorespondencyjny(SiedzibaIAdresResponse siedzibaIAdres) {
    return adresFacade.stworzAdres(siedzibaIAdres.siedziba());
  }

  private UUID zaktualizujPkdGlowny(Dzial3Response dzial3) {
    if (Objects.isNull(dzial3.przedmiotDzialalnosci()) ||
        dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().isEmpty()) {
      return null;
    }

    final var pkdGlownyResponse = dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().getFirst();
    final String pkd = pkdGlownyResponse.kodDzial() + pkdGlownyResponse.kodKlasa() + pkdGlownyResponse.kodPodklasa();

    return zaktualizujPkd(pkd);
  }

  private List<UUID> zaktualizujPkdDodatkowe(Dzial3Response dzial3) {
    if (Objects.isNull(dzial3.przedmiotDzialalnosci()) ||
        dzial3.przedmiotDzialalnosci().przedmiotPozostalejDzialalnosci().isEmpty()) {
      return Collections.emptyList();
    }

    List<UUID> listaUuidKodowPkdZapisanych = new ArrayList<>();

    if (Objects.nonNull(dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci()) &&
        dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().size() > 1) {
      usunDuplikatyPkd(
          List.copyOf(dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci()).subList(1, dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().size() - 1)
      ).stream()
          .filter(kodPkdResp -> Objects.nonNull(kodPkdResp) && !Objects.equals(kodPkdResp, dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().getFirst()))
          .map(kodPkdResponse -> kodPkdResponse.kodDzial() + kodPkdResponse.kodKlasa() + kodPkdResponse.kodPodklasa())
          .forEach(kod -> listaUuidKodowPkdZapisanych.add(zaktualizujPkd(kod)));
    }

    usunDuplikatyPkd(dzial3.przedmiotDzialalnosci().przedmiotPozostalejDzialalnosci()).stream()
        .filter(kodPkdResp -> Objects.nonNull(kodPkdResp) && !Objects.equals(kodPkdResp, dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().getFirst()))
        .map(kodPkdResponse -> kodPkdResponse.kodDzial() + kodPkdResponse.kodKlasa() + kodPkdResponse.kodPodklasa())
        .forEach(kod -> listaUuidKodowPkdZapisanych.add(zaktualizujPkd(kod)));
    return listaUuidKodowPkdZapisanych;
  }

  private Set<Dzial3Response.KodPkdResponse> usunDuplikatyPkd(List<Dzial3Response.KodPkdResponse> kodyPkd) {
    Set<Dzial3Response.KodPkdResponse> pkdUnikalne = new HashSet<>();
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
