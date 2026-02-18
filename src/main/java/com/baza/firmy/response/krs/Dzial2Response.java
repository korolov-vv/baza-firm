package com.baza.firmy.response.krs;

import java.util.List;
import lombok.Builder;

@Builder
public record Dzial2Response(
    ReprezentacjaResponse reprezentacja
) {

  @Builder
  public record ReprezentacjaResponse(
      String nazwaOrganu,
      String sposobReprezentacji,
      List<CzlonekZarzaduResponseResponse> sklad
  ) {
  }
}
