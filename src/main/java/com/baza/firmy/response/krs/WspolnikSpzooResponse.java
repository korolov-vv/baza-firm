package com.baza.firmy.response.krs;

import lombok.Builder;

@Builder
public record WspolnikSpzooResponse(
    NazwiskoResponse nazwisko,
    ImionaResponse imiona,
    IdentyfikatorResponse identyfikator,
    String posiadaneUdzialy,
    boolean czyPosiadaCaloscUdzialow
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
      String pesel,
      String nip,
      String regon
  ) {}
}
