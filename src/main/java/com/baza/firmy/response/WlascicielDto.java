package com.baza.firmy.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class WlascicielDto {

  private String imie;
  private String nazwisko;
  private String nip;
  private String regon;
}
