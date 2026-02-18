package com.baza.firmy.response.krs;

import lombok.Builder;

@Builder
public record DanePodmiotuResponse(
    String formaPrawna,
    IdentyfikatoryResponse identyfikatory,
    String nazwa,
    DaneOWczesniejszejRejestracjiResponse daneOWczesniejszejRejestracji,
    boolean czyProwadziDzialalnoscZInnymiPodmiotami,
    boolean czyPosiadaStatusOPP
) {

  @Builder
  public record IdentyfikatoryResponse(
      String nip,
      String regon,
      String krs,
      String numerEwidencyjnyWRejestrzeSadowym,
      String numerEwidencyjnyWRejestrzePrzedsiebiorcow
  ) {
  }

  @Builder
  public record DaneOWczesniejszejRejestracjiResponse(
      String nazwaPoprzedniegoRejestru,
      String sadProwadzacyRejestr
  ) {
  }

}
