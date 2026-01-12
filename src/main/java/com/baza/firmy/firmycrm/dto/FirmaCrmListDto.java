package com.baza.firmy.firmycrm.dto;

import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FirmaCrmListDto {
  
  private String nazwa;
  private String nip;

  private Pkd pkdGlowny;

  private String telefon;
  private String email;
  private String www;

  public Optional<Pkd> getPkdGlowny() {
    return Optional.ofNullable(pkdGlowny);
  }
}
