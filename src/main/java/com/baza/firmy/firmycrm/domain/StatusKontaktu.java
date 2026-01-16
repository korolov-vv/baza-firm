package com.baza.firmy.firmycrm.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusKontaktu {
    DO_KONTAKTU("Do kontaktu"),
    BRAK_ODPOWIEDZI("Brak odpowiedzi - zaplanowano następny kontakt"),
    PRZEKAZANO_DO_DALSZEJ_OBSLUGI("Przekazano do dalszej obsługi");

    private final String label;
}
