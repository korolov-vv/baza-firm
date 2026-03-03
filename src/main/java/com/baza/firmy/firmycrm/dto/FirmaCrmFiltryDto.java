package com.baza.firmy.firmycrm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirmaCrmFiltryDto {

    private UUID uuidFirmyKlienta;
    private String nazwa;
    private String pkd;
    private LocalDateTime dataRozpoczeciaOd;
    private LocalDateTime dataRozpoczeciaDo;
    private LocalDateTime dataOstatniegoKontaktuDo;
    private LocalDateTime dataNastepnegoKontaktuOd;
    private LocalDateTime dataDodaniaDoBazy;
    private String wojewodztwo;
    private String powiat;
    private String gmina;

    public Optional<UUID> getUuidFirmyKlienta() {
        return Optional.ofNullable(uuidFirmyKlienta);
    }

    public Optional<String> getNazwa() {
        return Optional.ofNullable(nazwa);
    }

    public Optional<String> getPkd() {
        return Optional.ofNullable(pkd);
    }

    public Optional<LocalDateTime> getDataRozpoczeciaOd() {
        return Optional.ofNullable(dataRozpoczeciaOd);
    }

    public Optional<LocalDateTime> getDataRozpoczeciaDo() {
        return Optional.ofNullable(dataRozpoczeciaDo);
    }

    public Optional<LocalDateTime> getDataOstatniegoKontaktuDo() {
        return Optional.ofNullable(dataOstatniegoKontaktuDo);
    }

    public Optional<LocalDateTime> getDataNastepnegoKontaktuOd() {
        return Optional.ofNullable(dataNastepnegoKontaktuOd);
    }

    public Optional<LocalDateTime> getDataDodaniaDoBazy() {
        return Optional.ofNullable(dataDodaniaDoBazy);
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

