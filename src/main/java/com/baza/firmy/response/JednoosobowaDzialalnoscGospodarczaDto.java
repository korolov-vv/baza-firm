package com.baza.firmy.response;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.entity.Osoba;
import com.baza.firmy.entity.Pkd;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class JednoosobowaDzialalnoscGospodarczaDto {

  private Long id;
  private UUID uuid;
  private String nazwa;
  
  @JsonProperty("id")
  private UUID ceidgId;

  private AdresDto adresDzialalnosci;
  private AdresDto adresKorespondencyjny;

  private Osoba wlasciciel;

  private String pkdGlowny;

  @Builder.Default
  private List<Pkd> pkd = new ArrayList<>();

  @Builder.Default
  private List<SpolkaDto> spolki = new ArrayList<>();

  private String dataRozpoczecia;
  private String dataZawieszenia;
  private String dataZakonczenia;
  private String dataWykreslenia;
  private String dataWznowienia;

  private BusinessStatus status;
  private int numerStatusu;
  private String telefon;
  private String email;
  private String www;
  private String adresDoreczenElektronicznych;
  private String innaFormaKonaktu;

  private String pelneInfo;

  private String link;
}
