package com.baza.firmy.response.krs;

import lombok.Builder;

@Builder
public record PozostaleInformacjeResponse(
    String czasNaJakiUtworzonyZostalPodmiot,
    String informacjaOLiczbieUdzialow
) {
}
