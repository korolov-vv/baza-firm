package com.baza.firmy.firmycrm.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SposobKontaktu {
    KONTAKT_TELEFONICZNY("Kontakt telefoniczny"),
    KONTAKT_MAILOWY("Kontakt mailowy"),
    LIST_TRADYCYJNY("List tradycyjny"),
    PRZEDSTAWICIEL_HANDLOWYK("Przedstawiciel handlowy");

    private final String label;
}
