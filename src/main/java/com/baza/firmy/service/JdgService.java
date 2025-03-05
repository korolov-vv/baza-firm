package com.baza.firmy.service;

import com.baza.firmy.entity.Adres;
import com.baza.firmy.entity.Jdg;
import com.baza.firmy.entity.Kraj;
import com.baza.firmy.entity.Osoba;
import com.baza.firmy.entity.Pkd;
import com.baza.firmy.mapper.JdgMapper;
import com.baza.firmy.repository.AdresRepository;
import com.baza.firmy.repository.JdgFilterSpecification;
import com.baza.firmy.repository.JdgRepository;
import com.baza.firmy.repository.KrajRepository;
import com.baza.firmy.repository.OsobaRepository;
import com.baza.firmy.repository.PkdRepository;
import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.response.JdgSzczegolyDto;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JdgService {

  private final JdgMapper jdgMapper;
  private final JdgRepository jdgRepository;
  private final AdresRepository adresRepository;
  private final OsobaRepository osobaRepository;
  private final PkdRepository pkdRepository;
  private final KrajRepository krajRepository;

  public List<JdgListDto> pobierzListeJdg(JdgFilterSpecification specification) {
    return jdgMapper.toJdgListDtoList(jdgRepository.findAll(specification));
  }

  @Transactional
  public UUID zapiszSzczegolyJdg(JdgSzczegolyDto jdgSzczegolyDto) {
    Jdg doZapisu = jdgRepository.findByCeidgId(jdgSzczegolyDto.getCeidgId())
        .map(jdg -> jdgMapper.toJdgEntity(jdg, jdgSzczegolyDto))
        .orElseGet(() -> jdgMapper.toJdgEntity(jdgSzczegolyDto));

    przygotujDoZapisu(doZapisu);
    Jdg saved = jdgRepository.saveAndFlush(doZapisu);

    log.info("Zapisano JDG: {}", saved.getCeidgId());

    return saved.getUuid();
  }

  public boolean czyIstniejePoCeidgId(UUID ceidgId) {
    return jdgRepository.existsByCeidgId(ceidgId);
  }

  private void przygotujDoZapisu(Jdg doZapisu) {
    zaktualizujAdresy(doZapisu);
    zaktualizujWlasciciela(doZapisu);
    zaktualizujKodyPkd(doZapisu);
  }

  private void zaktualizujAdresy(Jdg doZapisu) {
    Adres adresDzialalnosciZapisany = adresRepository.save(doZapisu.getAdresDzialalnosci());
    Adres adresKorespondencyjnyZapisany = adresRepository.save(doZapisu.getAdresKorespondencyjny());

    doZapisu.setAdresDzialalnosci(adresDzialalnosciZapisany);
    doZapisu.setAdresKorespondencyjny(adresKorespondencyjnyZapisany);
  }

  private void zaktualizujWlasciciela(Jdg doZapisu) {
        osobaRepository.findByNip(doZapisu.getWlasciciel().getNip())
            .ifPresentOrElse(doZapisu::setWlasciciel, zapiszWlasciciela(doZapisu));
  }

  Runnable zapiszWlasciciela(Jdg doZapisu) {
    return () -> {
      List<Kraj> krajeZapisane = zapiszKraje(doZapisu);
      doZapisu.getWlasciciel().setObywatelstwa(krajeZapisane);

      Osoba wlascicielZapisany = osobaRepository.save(doZapisu.getWlasciciel());
      doZapisu.setWlasciciel(wlascicielZapisany);
    };
  }
  
  private List<Kraj> zapiszKraje(Jdg doZapisu) {
    return doZapisu.getWlasciciel().getObywatelstwa().stream()
        .map(kraj -> krajRepository.findByKraj(kraj.getKraj())
            .orElseGet(() -> krajRepository.save(kraj)))
        .toList();
  }

  private void zaktualizujKodyPkd(Jdg doZapisu) {
    zaktualizujPkdGlowny(doZapisu);
    zaktualizujPkdDodatkowe(doZapisu);
  }

  private void zaktualizujPkdGlowny(Jdg doZapisu) {
    pkdRepository.findByKod(doZapisu.getPkdGlowny().getKod()).ifPresentOrElse(doZapisu::setPkdGlowny,
        () -> {
          Pkd pkdGlownyZapisany = pkdRepository.save(doZapisu.getPkdGlowny());
          doZapisu.setPkdGlowny(pkdGlownyZapisany);
        });
  }

  private void zaktualizujPkdDodatkowe(Jdg doZapisu) {
    List<Pkd> pkdZapisane = doZapisu.getPkd().stream()
        .filter(pkd -> !pkd.getKod().equals(doZapisu.getPkdGlowny().getKod()))
        .map(pkd -> pkdRepository.findByKod(pkd.getKod())
            .orElseGet(() -> pkdRepository.save(pkd)))
        .toList();

    doZapisu.setPkd(pkdZapisane);
  }
}
