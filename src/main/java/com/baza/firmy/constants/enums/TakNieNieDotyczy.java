package com.baza.firmy.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TakNieNieDotyczy {
  TAK("Tak", 1),
  NIE("Nie", 0),
  NIE_DOTYCZY("Nie dotyczy", 2);

  private final String label;
  private final int kod;

  public boolean czyWartoscEnumaWynosiNie() {
    return NIE == this;
  }
}
