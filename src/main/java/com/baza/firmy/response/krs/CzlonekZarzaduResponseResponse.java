package com.baza.firmy.response.krs;

import lombok.Builder;

@Builder
public record CzlonekZarzaduResponseResponse(
    NazwiskoResponse nazwisko,
    ImionaResponse imiona,
    IdentyfikatorResponse identyfikator,
    String funkcjaWOrganie,
    boolean czyZawieszona
) {

  @Builder
  public record NazwiskoResponse(
      String nazwiskoICzlon
  ) {}

  @Builder
  public record ImionaResponse(
      String imie
  ) {}

  @Builder
  public record IdentyfikatorResponse(
      String pesel
  ) {}
}
