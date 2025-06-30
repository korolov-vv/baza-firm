package com.baza.firmy.response.krs;

import lombok.Builder;

@Builder
public record OdpisAktualnyResponse(
    OdpisResponse odpis
) {
}