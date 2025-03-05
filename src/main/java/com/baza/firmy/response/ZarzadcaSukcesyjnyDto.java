package com.baza.firmy.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
class ZarzadcaSukcesyjnyDto {

  private String dataUstanowienia;
  private String imie;
  private String nazwisko;
  private String nip;
  private KrajDto obywatelstwo;
  private AdresDto adresKorespondencyjny;
  private String email;
  private String www;
  private String telefon;
  private String adresDoreczenElektronicznych;
  private String innaFormaKontaktu;
}
