package com.baza.firmy.podmiotygospodarcze.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Rejestr {
    CEIDG("CEIDG"),
    KRS("KRS"),
    REGON("REGON");

    private final String value;
}
