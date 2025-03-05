package com.baza.firmy.response;

import com.baza.firmy.constants.enums.BusinessStatus;
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
public class JdgSzczegolyDto {

  private UUID uuid;
  private String nazwa;

  @JsonProperty("id")
  private UUID ceidgId;

  private AdresDto adresDzialalnosci;
  private AdresDto adresKorespondencyjny;
  private List<AdresDto> adresyDzialanosciDodatkowe;

  private WlascicielDto wlasciciel;

  @Builder.Default
  private List<KrajDto> obywatelstwa = new ArrayList();

  private String pkdGlowny;
  @Builder.Default
  private List<String> pkd = new ArrayList<>();

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
  private String innaFormaKontaktu;

  private int wspolnoscMajatkowa;
  private String wspolnoscMajatkowaDataUstania;
  private String dataZgonu;
  private String zarzadSukcesyjnyDataUstanowienia;
  private String zarzadSukcesyjnyDataWygasniecia;
  @Builder.Default
  private List<String> podstawyPrawneWykreslenia = new ArrayList<>();
  @Builder.Default
  private List<ZakazDto> zakazy = new ArrayList<>();
  private SpolkaDto spolka;
  private UpadloscDto upadlosc;
  private ZarzadcaSukcesyjnyDto zarzadcaSukcesyjny;
  @Builder.Default
  private List<KwalifikacjeZawodoweDto> kwalifikacjeZawodowe = new ArrayList<>();
  @Builder.Default
  private List<UprawnieniaDto> uprawnienia = new ArrayList<>();
  @Builder.Default
  private List<OgraniczeniaDto> ograniczenia = new ArrayList<>();
  private OgraniczeniaZdolnosciPrawnejDto ograniczeniaZdolnosciPrawnej;

  private String link;
}
