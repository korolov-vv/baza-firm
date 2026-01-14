package com.baza.firmy.firmycrm.dto;

import com.baza.firmy.firmycrm.domain.StatusKontaktu;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FirmaCrmListDto {
  
  private String nazwa;
  private String nip;
  private String wojewodztwo;

  private Pkd pkdGlowny;

  private String telefon;
  private String email;

  private StatusKontaktu statusKontaktu;
  private LocalDateTime dataOstatniegoKontaktu;
  private String komentarz;

  public Optional<Pkd> getPkdGlowny() {
    return Optional.ofNullable(pkdGlowny);
  }

  public Optional<String> getWojewodztwo() {
    return Optional.ofNullable(wojewodztwo);
  }
}
