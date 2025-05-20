package com.baza.firmy.adresy.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdresDto {

  private UUID uuid;
  @EqualsAndHashCode.Include
  private String ulica;
  @EqualsAndHashCode.Include
  private String budynek;
  @EqualsAndHashCode.Include
  private String lokal;
  @EqualsAndHashCode.Include
  private String miasto;
  @EqualsAndHashCode.Include
  private String wojewodztwo;
  @EqualsAndHashCode.Include
  private String powiat;
  @EqualsAndHashCode.Include
  private String gmina;
  @EqualsAndHashCode.Include
  private String kraj;
  @EqualsAndHashCode.Include
  private String kod;
  private String terc;
  private String simc;
  private String ulic;

  @Override
  public String toString() {
    return (wojewodztwo != null ? wojewodztwo + ", " : "") +
        (gmina != null ? gmina + ", " : "") +
        (powiat != null ? powiat + ", " : "") +
        (kod != null ?  kod + ", " : "") +
        (miasto != null ? miasto + " " : "") +
        (ulica != null ? ulica + " " : "") +
        (budynek != null ? budynek : "") +
        (lokal != null ? "/" + lokal : "");
  }
}
