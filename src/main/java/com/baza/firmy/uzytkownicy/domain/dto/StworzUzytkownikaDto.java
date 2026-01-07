package com.baza.firmy.uzytkownicy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StworzUzytkownikaDto {

  private String email;
  private String podmiotGospodarcyId;
}
