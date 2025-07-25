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
class ZaktualizujPodmiotGospodarczyZJdgUseCase {

  private final OsobaQueryFacade osobaQueryFacade;
  private final AdresQueryFacade adresQueryFacade;
  private final PkdQueryFasade pkdQueryFasade;
  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;
  private final PodmiotyGospodarczeMapper podmiotyGospodarczeMapper;
  private final JdgService jdgService;

  @Transactional
  public UUID zaktualizujPodmiotGospodarczy(JdgSzczegolyDto jdgSzczegolyDto) {
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

    PodmiotGospodarczyEntity podmiotGospodarczyEntity = podmiotyGospodarczeRepository.findByNipAndDataRozpoczecia(
        jdgSzczegolyDto.getWlasciciel().getNip(), LocalDate.parse(jdgSzczegolyDto.getDataRozpoczecia())
        )
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Podmiot gospodarczy o podanym numere NIP: %s oraz dacie rozpoczęcia działalności: %s nie istnieje",
                jdgSzczegolyDto.getWlasciciel().getNip(), jdgSzczegolyDto.getDataRozpoczecia())));


    podmiotyGospodarczeMapper.toPodmiotGospodarczyEntity(podmiotGospodarczyEntity, jdgSzczegolyDto);

    if (adresDzialalnosciUuid != null) {
      podmiotGospodarczyEntity.setAdresDzialalnosci(adresQueryFacade.getAdresPoUuid(adresDzialalnosciUuid));
    }

    if (adresKorespondencyjnyUuid != null) {
      podmiotGospodarczyEntity.setAdresKorespondencyjny(adresQueryFacade.getAdresPoUuid(adresKorespondencyjnyUuid));
    }

    if (wlascicielUuid != null) {
      podmiotGospodarczyEntity.setWlasciciel(osobaQueryFacade.findByUuid(wlascicielUuid));
    }

    if (pkdGlownyUuid != null) {
      podmiotGospodarczyEntity.setPkdGlowny(pkdQueryFasade.findByUuid(pkdGlownyUuid));
    }

    if (!pozostalePkdUuidList.isEmpty()) {
      podmiotGospodarczyEntity.getPkd().clear();
      podmiotGospodarczyEntity.getPkd().addAll(pkdQueryFasade.findByUuidList(pozostalePkdUuidList));
    }

    if (podmiotGospodarczyEntity.getRokPkd() == null) {
      podmiotGospodarczyEntity.setRokPkd(podmiotGospodarczyEntity.getDataRozpoczecia().isBefore(LocalDate.of(2025, 01, 01)) ? "2007" : "2025");
    }

    return podmiotyGospodarczeRepository.save(podmiotGospodarczyEntity).getUuid();
  }
}
