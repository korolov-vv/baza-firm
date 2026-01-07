package com.baza.firmy.subscrypcje.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SubscrypcjaDto {

  private UUID uuid;
  private String nazwa;
  private String opis;
  private int iloscDostepnychFirm;
  private int okresTrwaniaWDniach;
}
