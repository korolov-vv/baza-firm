package com.baza.firmy.response.krs;

import com.baza.firmy.podmiotygospodarcze.domain.Rejestr;
import lombok.Builder;

@Builder
public record OdpisAktualnyResponse(
    OdpisResponse odpis,
    Rejestr rejestr
) {
}