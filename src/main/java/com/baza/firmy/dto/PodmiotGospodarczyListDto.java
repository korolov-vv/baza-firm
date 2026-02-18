package com.baza.firmy.dto;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PodmiotGospodarczyListDto {
  
  private String nazwa;
  private String nip;
  private String regon;
  private String krs;

  private AdresDto adresDzialalnosci;
  private AdresDto adresKorespondencyjny;

  private Pkd pkdGlowny;
  @Builder.Default
  private List<Pkd> pkd = new ArrayList<>();

  private String dataRozpoczecia;
  private String dataZawieszenia;
  private String dataZakonczenia;

  private BusinessStatus status;

  private String telefon;
  private String email;
  private String www;

  public Optional<AdresDto> getAdresDzialalnosci() {
    return Optional.ofNullable(adresDzialalnosci);
  }

  public Optional<Pkd> getPkdGlowny() {
    return Optional.ofNullable(pkdGlowny);
  }
}
