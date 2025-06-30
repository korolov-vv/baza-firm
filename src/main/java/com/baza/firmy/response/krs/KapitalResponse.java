package com.baza.firmy.response.krs;

import java.util.Map;
import lombok.Builder;

public record KapitalResponse(
    WysokoscKapitaluZakladowegoResponse wysokoscKapitaluZakladowego,
    Map<String, Object> wniesioneAporty
) {

  @Builder
  public record WysokoscKapitaluZakladowegoResponse(
      String wartosc,
      String waluta
  ) {}
}
