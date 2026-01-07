package com.baza.firmy.uzytkownicy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StworzSubscrypcjeUzytkownikaDto {

  private UUID uzytkownikId;
  private UUID subscrypcjaId;
}
