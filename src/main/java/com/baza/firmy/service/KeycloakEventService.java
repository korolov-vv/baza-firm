package com.baza.firmy.service;

import com.baza.firmy.dto.KeycloakEventDto;
import com.baza.firmy.integration.keycloak.KeycloakUserClient;
import com.baza.firmy.integration.keycloak.KeycloakUserData;
import com.baza.firmy.uzytkownicy.domain.UzytkownicyFacade;
import com.baza.firmy.uzytkownicy.dto.StworzUzytkownikaDto;
import com.baza.firmy.uzytkownicy.query.UzytkownicyQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakEventService {

    private final UzytkownicyFacade uzytkownicyFacade;
    private final UzytkownicyQueryFacade uzytkownicyQueryFacade;
    private final KeycloakUserClient keycloakUserClient;

    @Transactional
    public void processEvent(KeycloakEventDto event) {
        if (event == null || event.getType() == null) {
            log.warn("Received null event or event type");
            return;
        }

        log.info("Processing Keycloak event: type={}, userId={}, time={}",
                 event.getType(), event.getUserId(), event.getEventTime());

        switch (event.getType()) {
            case "access.REGISTER":
            case "access.register":
                handleUserRegistration(event);
                break;
            case "UPDATE_PROFILE":
            case "update_profile":
                handleProfileUpdate(event);
                break;
            default:
                log.debug("Unhandled event type: {}", event.getType());
        }
    }

    private void handleUserRegistration(KeycloakEventDto event) {
        log.info("Handling user registration for userId: {}", event.getUserId());

        try {
            // Pobierz username z eventu (może być email lub inna wartość)
            String email = event.getDetails() != null
                    ? event.getDetails().get("username")
                    : null;

            if (email == null || email.isBlank()) {
                log.warn("No email found for Keycloak user: {}", event.getUserId());
                return;
            }

            // Sprawdź czy użytkownik już istnieje w bazie
            if (uzytkownicyQueryFacade.existsByEmail(email)) {
                log.warn("Użytkownik z email: {} już istnieje w bazie", email);
                return;
            }

            // Pobierz pełne dane użytkownika z Keycloak Admin API
            KeycloakUserData userData = keycloakUserClient.getUserByUsername(email)
                        .orElseThrow(() -> new IllegalStateException(
                            "W Keycloak nie znaleziono uzytkownika z username: " + email));

            if (userData == null) {
                log.error("Nie udało się pobrać danych uzytkownika z Keycloak dla userId: {}", event.getUserId());
                return;
            }

            // Utwórz użytkownika w lokalnej bazie danych
            StworzUzytkownikaDto stworzUzytkownikaDto = StworzUzytkownikaDto.builder()
                    .keycloakUuid(UUID.fromString(userData.getId()))
                    .email(userData.getUsername())
                    .nip(userData.getNip())
                    .build();

            UUID userId = uzytkownicyFacade.stworzUzytkownika(stworzUzytkownikaDto);
            log.info("Successfully created user with UUID: {} from Keycloak registration (email: {}, nip: {})",
                     userId, email, "nip");

        } catch (Exception e) {
            log.error("Error handling user registration for userId: {}", event.getUserId(), e);
            throw e;
        }
    }


    private void handleProfileUpdate(KeycloakEventDto event) {
        log.debug("Profile update event for userId: {}", event.getUserId());
        // Tutaj możesz dodać logikę do obsługi aktualizacji profilu użytkownika
        // Na przykład: synchronizacja danych użytkownika z Keycloak
    }
}

