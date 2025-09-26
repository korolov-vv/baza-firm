package com.baza.firmy.danezportaluzewn.query.dto;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PortalZewnKategoriaDto {

    private UUID uuid;
    private String kategoria;
    private String path;
    private StatusPobieraniaEnum statusPobierania;
    private String blad;
}
