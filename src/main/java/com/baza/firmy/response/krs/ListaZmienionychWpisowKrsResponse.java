package com.baza.firmy.response.krs;

import java.util.List;
import lombok.Builder;

@Builder
public record ListaZmienionychWpisowKrsResponse(
    List<String> numeryKrs
) {
}
