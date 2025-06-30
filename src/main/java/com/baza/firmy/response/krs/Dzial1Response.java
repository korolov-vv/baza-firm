package com.baza.firmy.response.krs;

import java.util.List;
import lombok.Builder;

@Builder
public record Dzial1Response(
    DanePodmiotuResponse danePodmiotu,
    SiedzibaIAdresResponse siedzibaIAdres,
    UmowaIStatutResponse umowaIStatut,
    PozostaleInformacjeResponse pozostaleInformacje,
    List<WspolnikSpzooResponse> wspolnicySpzoo,
    KapitalResponse kapital
) {
}
