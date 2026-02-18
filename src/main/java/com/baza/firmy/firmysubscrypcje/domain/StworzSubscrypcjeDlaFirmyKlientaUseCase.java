package com.baza.firmy.firmysubscrypcje.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import com.baza.firmy.subscrypcje.query.SubscrypcjaViewEntity;
import com.baza.firmy.subscrypcje.query.SubscrypcjeQueryFacade;
import com.baza.firmy.uzytkownicy.dto.StworzSubscrypcjeDlaFirmyKlientaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzSubscrypcjeDlaFirmyKlientaUseCase {

  private static final String TRIAL = "TRIAL";

  private final FirmySubscrypcjeRepository firmySubscrypcjeRepository;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
  private final SubscrypcjeQueryFacade subscrypcjeQueryFacade;

  public UUID stworzSubscrypcjeDlaFirmyKlienta(StworzSubscrypcjeDlaFirmyKlientaDto stworzSubscrypcjeDlaFirmyKlientaDto) {
    PodmiotGospodarczyViewEntity firma = podmiotyGospodarczeQueryFacade.pobierzPoUuid(stworzSubscrypcjeDlaFirmyKlientaDto.getFirmaUuid())
            .orElseThrow(() -> new IllegalArgumentException("Firma o UUID: " + stworzSubscrypcjeDlaFirmyKlientaDto.getFirmaUuid() + " nie istnieje"));
    SubscrypcjaViewEntity subscrypcja = subscrypcjeQueryFacade.findByUuid(stworzSubscrypcjeDlaFirmyKlientaDto.getSubscrypcjaUuid())
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja o UUID: " + stworzSubscrypcjeDlaFirmyKlientaDto.getSubscrypcjaUuid() + " nie istnieje"));

    FirmaSubscrypcjaEntity uzytkownikEntity = stworzSubscrypcjeDlaFirmyKlientaEntity(firma, subscrypcja);

    return firmySubscrypcjeRepository.save(uzytkownikEntity).getUuid();
  }

  private FirmaSubscrypcjaEntity stworzSubscrypcjeDlaFirmyKlientaEntity(PodmiotGospodarczyViewEntity firma, SubscrypcjaViewEntity subscrypcja) {
    return FirmaSubscrypcjaEntity.builder()
            .uuid(UUID.randomUUID())
            .firmaKlient(firma)
            .subscrypcja(subscrypcja)
            .aktywnaOd(LocalDate.now())
            .aktywnaDo(LocalDate.now().plusDays(subscrypcja.getOkresTrwaniaWDniach()))
            .statusSubscrypcji(TRIAL.equals(subscrypcja.getNazwa()) ? StatusSubscrypcji.AKTYWNA : StatusSubscrypcji.OCZEKUJE_NA_PLATNOSC)
            .build();
  }
}
