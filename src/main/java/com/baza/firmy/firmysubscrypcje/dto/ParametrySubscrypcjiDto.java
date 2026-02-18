package com.baza.firmy.firmysubscrypcje.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
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

    public Optional<String> getPkd() {
        return Optional.ofNullable(pkd);
    }

    public Optional<LocalDateTime> getDataRozpoczeciaOd() {
        return Optional.ofNullable(dataRozpoczeciaOd);
    }

    public Optional<LocalDateTime> getDataRozpoczeciaDo() {
        return Optional.ofNullable(dataRozpoczeciaDo);
    }

    public Optional<String> getWojewodztwo() {
        return Optional.ofNullable(wojewodztwo);
    }

    public Optional<String> getPowiat() {
        return Optional.ofNullable(powiat);
    }

    public Optional<String> getGmina() {
        return Optional.ofNullable(gmina);
    }
}
