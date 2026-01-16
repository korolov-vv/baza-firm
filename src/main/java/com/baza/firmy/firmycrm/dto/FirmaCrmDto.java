package com.baza.firmy.firmycrm.dto;

import com.baza.firmy.firmycrm.domain.SposobKontaktu;
import com.baza.firmy.firmycrm.domain.StatusKontaktu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirmaCrmDto {

  private int version;
  private UUID uuid;
  private String nazwa;
  private String nip;
  private String regon;
  private String adresKorespondencyjny;
  private String adresDzialalnosci;
  private String pkdGlowny;
  private String pozostalePkd;
  private String telefon;
  private String email;
  private String stronaWww;
  private StatusKontaktu statusKontaktu;
  private SposobKontaktu sposobKontaktu;
  private LocalDateTime dataOstatniegoKontaktu;
  private LocalDateTime dataNastepnegoKontaktu;
  private String komentarz;
}

