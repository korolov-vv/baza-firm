package com.baza.firmy.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class WlascicielDto {

  private UUID uuid;
  private String imie;
  private String nazwisko;
  private String nip;
  private boolean nipUchylony;
  private boolean nipUniewazniony;
  private String regon;
}
