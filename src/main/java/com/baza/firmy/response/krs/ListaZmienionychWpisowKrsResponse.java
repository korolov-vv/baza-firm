package com.baza.firmy.response.krs;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import java.util.List;
import lombok.Builder;

@Builder
public record ListaZmienionychWpisowKrsResponse(
    List<String> numeryKrs,
    StatusPobieraniaEnum statusPobierania
) {
}
