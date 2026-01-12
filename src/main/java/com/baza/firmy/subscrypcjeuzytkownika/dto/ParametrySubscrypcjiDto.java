package com.baza.firmy.subscrypcjeuzytkownika.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ParametrySubscrypcjiDto {

    private UUID uuid;
    private int version;
    private String pkd;
    private LocalDateTime dataRozpoczeciaOd;
    private LocalDateTime dataRozpoczeciaDo;
    private String wojewodztwo;
    private String powiat;
    private String gmina;
}
