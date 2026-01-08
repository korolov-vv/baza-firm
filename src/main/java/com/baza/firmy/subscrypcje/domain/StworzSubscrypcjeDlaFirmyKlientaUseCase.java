package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
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

  private final UzytkownicySubscrypcjeRepository uzytkownicySubscrypcjeRepository;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
  private final SubscrypcjeQueryFacade subscrypcjeQueryFacade;

  public UUID stworzSubscrypcjeDlaFirmyKlienta(StworzSubscrypcjeDlaFirmyKlientaDto stworzSubscrypcjeDlaFirmyKlientaDto) {
    PodmiotGospodarczeViewEntity firma = podmiotyGospodarczeQueryFacade.pobierzPoUuid(stworzSubscrypcjeDlaFirmyKlientaDto.getFirmaUuid())
            .orElseThrow(() -> new IllegalArgumentException("Firma o UUID: " + stworzSubscrypcjeDlaFirmyKlientaDto.getFirmaUuid() + " nie istnieje"));
    SubscrypcjaViewEntity subscrypcja = subscrypcjeQueryFacade.findByUuid(stworzSubscrypcjeDlaFirmyKlientaDto.getSubscrypcjaId())
            .orElseThrow(() -> new IllegalArgumentException("Subscrypcja o UUID: " + stworzSubscrypcjeDlaFirmyKlientaDto.getSubscrypcjaId() + " nie istnieje"));

    UzytkownikSubscrypcjaEntity uzytkownikEntity = stworzSubscrypcjeDlaFirmyKlienta(firma, subscrypcja);

    return uzytkownicySubscrypcjeRepository.save(uzytkownikEntity).getUuid();
  }

  private UzytkownikSubscrypcjaEntity stworzSubscrypcjeDlaFirmyKlienta(PodmiotGospodarczeViewEntity firma, SubscrypcjaViewEntity subscrypcja) {
    return UzytkownikSubscrypcjaEntity.builder()
            .firmaKlient(firma)
            .subscrypcja(subscrypcja)
            .aktywnaOd(LocalDate.now())
            .aktywnaDo(LocalDate.now().plusDays(subscrypcja.getOkresTrwaniaWDniach()))
            .statusSubscrypcji(StatusSubscrypcji.OCZEKUJE_NA_PLATNOSC)
            .build();
  }
}
