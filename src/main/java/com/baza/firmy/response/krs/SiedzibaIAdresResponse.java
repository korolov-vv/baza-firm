package com.baza.firmy.response.krs;

import lombok.Builder;

@Builder
public record SiedzibaIAdresResponse(
    AdresKrsResponse siedziba,
    AdresKrsResponse adres,
    String adresPocztyElektronicznej
) {

  @Builder
  public record AdresKrsResponse(
      String kraj,
      String wojewodztwo,
      String powiat,
      String gmina,
      String miejscowosc,
      String ulica,
      String nrDomu,
      String nrLokalu,
      String kodPocztowy,
      String poczta
      ) {
  }

}
