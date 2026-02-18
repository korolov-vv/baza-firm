package com.baza.firmy.response.krs;

import java.util.List;
import lombok.Builder;

@Builder
public record UmowaIStatutResponse(
    List<InformacjaOZawarciuZmianieUmowyStatutuResponse> informacjaOZawarciuZmianieUmowyStatutu
) {

  @Builder
  public record InformacjaOZawarciuZmianieUmowyStatutuResponse(
      String zawarcieZmianaUmowyStatutu
  ) {
  }
}
