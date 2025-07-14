package com.baza.firmy.dto;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.constants.enums.BusinessStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class JdgListDto {
  
  private String nazwa;
  private String nip;
  private String regon;
  private String krs;

  private AdresDto adresDzialalnosci;
  private AdresDto adresKorespondencyjny;

  private String pkdGlowny;
  @Builder.Default
  private List<String> pkd = new ArrayList<>();

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

  public Optional<String> getPkdGlowny() {
    return Optional.ofNullable(pkdGlowny);
  }
}
