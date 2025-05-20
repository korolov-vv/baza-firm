package com.baza.firmy.kraje.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class KrajDto {

  private UUID uuid;
  private String symbol;
  private String kraj;
}
