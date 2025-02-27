package com.baza.firmy.response;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.entity.Osoba;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CeidgListDto {

  private String nazwa;
  
  @JsonProperty("id")
  private UUID ceidgId;

  private AdresDto adresDzialalnosci;
  private Osoba wlasciciel;

  private String dataRozpoczecia;

  private BusinessStatus status;
  private String telefon;
  private String email;
  private String www;
  private String adresDoreczenElektronicznych;
  private String innaFormaKonaktu;

  private String pelneInfo;

  private String link;
}
