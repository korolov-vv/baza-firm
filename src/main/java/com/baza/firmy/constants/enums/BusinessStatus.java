package com.baza.firmy.constants.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessStatus {
  AKTYWNY("Aktywny"),
  WYKRESLONY("Wykreślony"),
  ZAWIESZONY("Zawieszony"),
  OCZEKUJE_NA_ROZPOCZECIE_DZIALANOSCI("Oczekuje na rozpoczęcie działalności"),
  WYLACZNIE_W_FORMIE_SPOLKI("Działalność prowadzona wyłącznie w formie spółki cywilnej");

  private final String raportLabel;

  public static BusinessStatus getByRaportLabel(String raportLabel) {
    return Arrays.stream(BusinessStatus.values())
        .filter(status -> status.getRaportLabel().equalsIgnoreCase(raportLabel)).findFirst().get();
  }
}
