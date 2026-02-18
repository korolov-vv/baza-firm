package com.baza.firmy.podmiotygospodarcze.domain.dto;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.kraje.domain.KrajDto;
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
