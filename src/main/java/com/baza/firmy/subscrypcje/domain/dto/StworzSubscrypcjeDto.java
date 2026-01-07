package com.baza.firmy.subscrypcje.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StworzSubscrypcjeDto {

  private String nazwa;
  private String opis;
  private int iloscDostepnychFirm;
  private int okresTrwaniaWDniach;
}
