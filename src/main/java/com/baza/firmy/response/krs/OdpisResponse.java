package com.baza.firmy.response.krs;

import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record OdpisResponse(
    String rodzaj,
    NaglowekAResponse naglowekA
) {
  @Builder
  public record NaglowekAResponse(
      String rejestr,
      String numerKRS,
      String dataCzasOdpisu,
      String stanZDnia,
      String dataRejestracjiWKRS,
      int numerOstatniegoWpisu,
      String dataOstatniegoWpisu,
      String sygnaturaAktSprawyDotyczacejOstatniegoWpisu,
      String oznaczenieSaduDokonujacegoOstatniegoWpisu,
      int stanPozycji,
      List<Wpis> dane
  ) {
  }

  @Builder
  public record Wpis(
      Dzial1Response dzial1,
      Dzial2Response dzial2,
      Dzial3Response dzial3,
      Map<String, Object> dzial4,
      Map<String, Object> dzial5,
      Map<String, Object> dzial6
  ) {
  }
}
