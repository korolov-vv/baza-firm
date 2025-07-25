package com.baza.firmy.danezportaluzewn.domain.dto;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FirmaPortalZewnDto {

  private String krs;
  private String nip;
  private String regon;
  private String link;
  private String adres;
  private String email;
  private String nazwa;
  private String telefon;
  private String stronaWww;

  public Optional<String> getKrs() {
    return Optional.ofNullable(krs);
  }

  public Optional<String> getNip() {
    return Optional.ofNullable(nip);
  }

  public Optional<String> getRegon() {
    return Optional.ofNullable(regon);
  }

  public Optional<String> getAdres() {
    return Optional.ofNullable(adres);
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(email);
  }

  public Optional<String> getNazwa() {
    return Optional.ofNullable(nazwa);
  }

  public Optional<String> getTelefon() {
    return Optional.ofNullable(telefon);
  }

  public Optional<String> getStronaWww() {
    return Optional.ofNullable(stronaWww);
  }
}
