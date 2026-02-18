package com.baza.firmy.podmiotygospodarcze.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
class KwalifikacjeZawodoweDto {

  private String dataEgzaminu;
  private String dataUniewaznienia;
  private String rodzajKwalifikacji;
  private String zawod;
  private String wydanyPrzez;
  private String symbolZawodu;
  private String wprowadzonyPrzez;
}
