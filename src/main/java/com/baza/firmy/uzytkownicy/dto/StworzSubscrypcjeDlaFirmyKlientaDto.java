package com.baza.firmy.uzytkownicy.dto;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StworzSubscrypcjeDlaFirmyKlientaDto {

  private UUID firmaUuid;
  private UUID subscrypcjaId;
  private StatusSubscrypcji statusSubscrypcji;
}
