package com.baza.firmy.osoby.domain.dto;

import com.baza.firmy.kraje.domain.KrajDto;
import java.util.ArrayList;
import java.util.List;
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
  @Builder.Default
  private List<KrajDto> obywatelstwa = new ArrayList();
}
