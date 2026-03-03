package com.baza.firmy.firmysubscrypcje.dto;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FirmaSubscrypcjaDto {

  private UUID uuid;
  private UUID uuidFirmyKlienta;
  private String nazwa;
  private String opis;
  private int iloscDostepnychFirm;
  private LocalDate aktywnaOd;
  private LocalDate aktywnaDo;
  private StatusSubscrypcji statusSubscrypcji;
  private ParametrySubscrypcjiDto parametrySubscrypcji;
}
