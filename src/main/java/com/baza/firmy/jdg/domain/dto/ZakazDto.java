package com.baza.firmy.jdg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
class ZakazDto {

  private String typ;
  private String opis;
  private String okres;
  private String dataWydania;
  private String dataUprawomocnienia;
}
