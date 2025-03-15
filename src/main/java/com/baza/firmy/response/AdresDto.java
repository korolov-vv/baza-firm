package com.baza.firmy.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AdresDto {

  private UUID uuid;
  private String ulica;
  private String budynek;
  private String lokal;
  private String miasto;
  private String wojewodztwo;
  private String powiat;
  private String gmina;
  private String kraj;
  private String kod;
  private String terc;
  private String simc;
  private String ulic;

  @Override
  public String toString() {
    return (ulica != null ? ulica + " " : "") +
        (budynek != null ? budynek : "") +
        (lokal != null ? "/" + lokal : " ") +
        (kod != null ? ", " + kod : "") +
        (miasto != null ? ", " + miasto : "") +
        (powiat != null ? ", " + powiat : "") +
        (gmina != null ? ", " + gmina : "") +
        (wojewodztwo != null ? ", " + wojewodztwo : "");
  }
}
