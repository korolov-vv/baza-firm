package com.baza.firmy.uzytkownicy.dto;

import com.baza.firmy.firmysubscrypcje.dto.FirmaSubscrypcjaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UzytkownikDto {

  private UUID uuid;
  private int version;
  private String email;
  private String podmiotGospodarczyUuid;
  private String nazwaFirmy;
  private String nip;
  private boolean czyEmailPotwierdzony;
  private FirmaSubscrypcjaDto aktywnaSubscrypcja;

  public Optional<FirmaSubscrypcjaDto> getAktywnaSubscrypcja() {
    return Optional.ofNullable(aktywnaSubscrypcja);
  }

}
