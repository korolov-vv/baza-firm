package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.domain.AdresFacade;
import com.baza.firmy.osoby.domain.dto.ReprezentacjaDto;
import com.baza.firmy.osoby.domain.dto.ReprezentantDto;
import com.baza.firmy.osoby.domain.dto.WlascicielDto;
import com.baza.firmy.pkd.domain.PkdFasade;
import com.baza.firmy.pkd.domain.dto.PkdDto;
import com.baza.firmy.pkd.query.PkdQueryFasade;
import com.baza.firmy.pkd.query.PkdViewEntity;
import com.baza.firmy.response.krs.CzlonekZarzaduResponseResponse;
import com.baza.firmy.response.krs.Dzial2Response;
import com.baza.firmy.response.krs.Dzial3Response;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import com.baza.firmy.response.krs.SiedzibaIAdresResponse;
import com.baza.firmy.response.krs.WspolnikSpzooResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class SpolkaService {

  private final AdresFacade adresFacade;
  private final PkdFasade pkdFasade;
  private final PkdQueryFasade pkdQueryFasade;

  void ustawReprezentacje(OdpisAktualnyResponse odpisAktualnyResponse, PodmiotGospodarczyEntity podmiotGospodarczyEntity) {
    final var reprezentacja = odpisAktualnyResponse.odpis().dane().dzial2().reprezentacja();
    if (Objects.isNull(reprezentacja)) {
      podmiotGospodarczyEntity.setReprezentacja(null);
      return;
    }
    podmiotGospodarczyEntity.setReprezentacja(
        ReprezentacjaDto.builder()
            .nazwaOrganu(reprezentacja.nazwaOrganu())
            .sposobReprezentacji(reprezentacja.sposobReprezentacji())
            .sklad(ustawSklad(reprezentacja))
            .build()
    );
  }

  void ustawWspolnikow(OdpisAktualnyResponse odpisAktualnyResponse, PodmiotGospodarczyEntity podmiotGospodarczyEntity) {
    podmiotGospodarczyEntity.getWspolnicySpzoo().clear();

    if (Objects.isNull(odpisAktualnyResponse) || Objects.isNull(odpisAktualnyResponse.odpis()) ||
        Objects.isNull(odpisAktualnyResponse.odpis().dane()) ||
        Objects.isNull(odpisAktualnyResponse.odpis().dane().dzial1()) ||
        Objects.isNull(odpisAktualnyResponse.odpis().dane().dzial1().wspolnicySpzoo())) {
      return;
    }

    podmiotGospodarczyEntity.getWspolnicySpzoo().addAll(
        odpisAktualnyResponse.odpis().dane().dzial1().wspolnicySpzoo().stream()
            .map(this::stworzWlasciciela)
            .toList()
      );
  }

  UUID zaktualizujAdresDzialalnoszci(SiedzibaIAdresResponse siedzibaIAdres) {
    return adresFacade.stworzAdres(siedzibaIAdres.adres());
  }
  UUID zaktualizujAdresKorespondencyjny(SiedzibaIAdresResponse siedzibaIAdres) {
    return adresFacade.stworzAdres(siedzibaIAdres.siedziba());
  }

  private List<ReprezentantDto> ustawSklad(Dzial2Response.ReprezentacjaResponse reprezentacja) {
    if (Objects.isNull(reprezentacja) || Objects.isNull(reprezentacja.sklad())) {
      return Collections.emptyList();
    }
    return reprezentacja.sklad().stream()
        .map(this::stworzReprezentanta)
        .toList();
  }

  UUID zaktualizujPkdGlowny(Dzial3Response dzial3) {
    if (Objects.isNull(dzial3.przedmiotDzialalnosci()) ||
        dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().isEmpty()) {
      return null;
    }

    final var pkdGlownyResponse = dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().getFirst();
    final String pkd = pkdGlownyResponse.kodDzial() + pkdGlownyResponse.kodKlasa() + Optional.ofNullable(pkdGlownyResponse.kodPodklasa()).orElse(Strings.EMPTY);

    return zaktualizujPkd(pkd);
  }

  List<UUID> zaktualizujPkdDodatkowe(Dzial3Response dzial3) {
    if (Objects.isNull(dzial3.przedmiotDzialalnosci()) ||
        Objects.isNull(dzial3.przedmiotDzialalnosci().przedmiotPozostalejDzialalnosci()) ||
        dzial3.przedmiotDzialalnosci().przedmiotPozostalejDzialalnosci().isEmpty()) {
      return Collections.emptyList();
    }

    Set<UUID> listaUuidKodowPkdZapisanych = new HashSet<>();

    if (Objects.nonNull(dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci()) &&
        dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().size() > 1) {
      List.copyOf(dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci()).subList(1, dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().size() - 1).stream()
          .filter(kodPkdResp -> Objects.nonNull(kodPkdResp) && !Objects.equals(kodPkdResp, dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().getFirst()))
          .map(kodPkdResponse -> kodPkdResponse.kodDzial() + kodPkdResponse.kodKlasa() + Optional.ofNullable(kodPkdResponse.kodPodklasa()).orElse(Strings.EMPTY))
          .forEach(kod -> listaUuidKodowPkdZapisanych.add(zaktualizujPkd(kod)));
    }

    dzial3.przedmiotDzialalnosci().przedmiotPozostalejDzialalnosci().stream()
        .filter(kodPkdResp -> Objects.nonNull(kodPkdResp) &&
            !Objects.equals(kodPkdResp, getPkdPrzewazajacy(dzial3)))
        .map(kodPkdResponse -> kodPkdResponse.kodDzial() + kodPkdResponse.kodKlasa() + Optional.ofNullable(kodPkdResponse.kodPodklasa()).orElse(Strings.EMPTY))
        .forEach(kod -> listaUuidKodowPkdZapisanych.add(zaktualizujPkd(kod)));
    return List.copyOf(listaUuidKodowPkdZapisanych);
  }

  private Dzial3Response.KodPkdResponse getPkdPrzewazajacy(Dzial3Response dzial3) {
    if (Objects.isNull(dzial3.przedmiotDzialalnosci()) ||
        Objects.isNull(dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci()) ||
        dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().isEmpty()) {
      return null;
    }
    return dzial3.przedmiotDzialalnosci().przedmiotPrzewazajacejDzialalnosci().getFirst();
  }

  private UUID zaktualizujPkd(String pkd) {
    return pkdQueryFasade.findByKod(pkd.trim())
        .map(PkdViewEntity::getUuid)
        .orElseGet(() -> pkdFasade.stworzPkd(PkdDto.builder()
            .uuid(UUID.randomUUID())
            .kod(pkd.trim())
            .build()));
  }

  private ReprezentantDto stworzReprezentanta(CzlonekZarzaduResponseResponse reprezentant) {
    return ReprezentantDto.builder()
        .funkcjaWOrganie(reprezentant.funkcjaWOrganie())
        .czyZawieszona(reprezentant.czyZawieszona())
        .imie(Objects.nonNull(reprezentant.imiona()) ? reprezentant.imiona().imie() : Strings.EMPTY)
        .nazwisko(Objects.nonNull(reprezentant.nazwisko()) ? reprezentant.nazwisko().nazwiskoICzlon() : Strings.EMPTY)
        .pesel(Objects.nonNull(reprezentant.identyfikator()) ? reprezentant.identyfikator().pesel() : Strings.EMPTY)
        .nip(Objects.nonNull(reprezentant.identyfikator()) ? reprezentant.identyfikator().nip() : Strings.EMPTY)
        .regon(Objects.nonNull(reprezentant.identyfikator()) ? reprezentant.identyfikator().regon() : Strings.EMPTY)
        .build();
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
}
