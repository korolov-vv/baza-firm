package com.baza.firmy.osoby.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ReprezentantDto {

  private String imie;
  private String nazwisko;
  private String pesel;
  private String nip;
  private String regon;
  private String funkcjaWOrganie;
  private boolean czyZawieszona;
}
