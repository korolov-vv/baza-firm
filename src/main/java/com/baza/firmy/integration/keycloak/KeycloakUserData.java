package com.baza.firmy.integration.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO zawierający dane użytkownika pobrane z Keycloak
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUserData {

    /**
     * UUID użytkownika w Keycloak
     */
    private String id;

    /**
     * Nazwa użytkownika (zwykle email)
     */
    private String username;

    /**
     * Czy konto jest aktywne
     */
    private Boolean enabled;

    /**
     * Czy email jest zweryfikowany
     */
    private Boolean emailVerified;

    /**
     * NIP firmy (custom attribute)
     */
    private String nip;
}

