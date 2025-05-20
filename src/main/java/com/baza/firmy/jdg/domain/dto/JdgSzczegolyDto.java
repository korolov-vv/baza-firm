package com.baza.firmy.jdg.domain.dto;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.dto.JdgSzczegoly;
import com.baza.firmy.kraje.domain.KrajDto;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class JdgSzczegolyDto implements JdgSzczegoly {

  private int version;
  private UUID uuid;
  private String nazwa;

  @JsonProperty("id")
  private UUID ceidgId;

  private AdresDto adresDzialalnosci;
  private AdresDto adresKorespondencyjny;
  private List<AdresDto> adresyDzialanosciDodatkowe;

  private StworzWlascicielaDto wlasciciel;

  @Builder.Default
  private List<KrajDto> obywatelstwa = new ArrayList<>();

  private String pkdGlowny;
  @Builder.Default
  private List<String> pkd = new ArrayList<>();
  private String rokPkd;


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

  @Override
  public Optional<String> getPkdGlowny() {
    return Optional.ofNullable(pkdGlowny);
  }
}
