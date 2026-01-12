package com.baza.firmy.firmycrm.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SposobKontaktu {
    KONTAKT_TELEFONICZNY("Kontakt telefoniczny"),
    KONTAKT_MAILOWY("Kontakt mailowy"),
    LIST_TRADYCYJNY("List tradycyjny"),
    PRZEDSTAWICIEL_HANDLOWYK("Przedstawiciel handlowy");

    private final String value;
}
