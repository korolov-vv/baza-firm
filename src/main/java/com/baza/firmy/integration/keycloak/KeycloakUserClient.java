package com.baza.firmy.integration.keycloak;

import com.baza.firmy.configuration.properties.KeycloakBazafirmCustomerProperties;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class KeycloakUserClient {

    private final Keycloak keycloakCustomer;
    private final KeycloakBazafirmCustomerProperties keycloakProperties;

    public KeycloakUserClient(
            @Qualifier("keycloakBazafirmCustomer") Keycloak keycloakCustomer,
            KeycloakBazafirmCustomerProperties keycloakProperties) {
        this.keycloakCustomer = keycloakCustomer;
        this.keycloakProperties = keycloakProperties;
    }

    /**
     * Pobiera dane użytkownika z Keycloak po username (email)
     *
     * @param username username użytkownika (zwykle email)
     * @return Optional z danymi użytkownika
     */
    public Optional<KeycloakUserData> getUserByUsername(String username) {
        try {
            log.debug("Fetching user from Keycloak with username: {}", username);

            // Odśwież token przed requestem aby uniknąć 401
            refreshToken();

            List<UserRepresentation> users = keycloakCustomer
                    .realm(keycloakProperties.getRealm())
                    .users()
                    .search(username, true);

            if (users.isEmpty()) {
                log.warn("User not found in Keycloak with username: {}", username);
                return Optional.empty();
            }

            if (users.size() > 1) {
                log.warn("Multiple users found with username: {}, taking first one", username);
            }

            UserRepresentation user = users.getFirst();
            KeycloakUserData userData = mapToUserData(user);

            log.debug("Successfully fetched user data for username: {}, userId: {}", username, user.getId());
            return Optional.of(userData);

        } catch (Exception e) {
            log.error("Error fetching user from Keycloak with username: {}", username, e);
            return Optional.empty();
        }
    }

    /**
     * Pobiera dane użytkownika z Keycloak po userId
     *
     * @param userId UUID użytkownika w Keycloak
     * @return Optional z danymi użytkownika
     */
    public Optional<KeycloakUserData> getUserById(String userId) {
        try {
            log.debug("Fetching user from Keycloak with userId: {}", userId);

            // Odśwież token przed requestem aby uniknąć 401
            refreshToken();

            UserRepresentation user = keycloakCustomer
                    .realm(keycloakProperties.getRealm())
                    .users()
                    .get(userId)
                    .toRepresentation();

            KeycloakUserData userData = mapToUserData(user);

            log.debug("Successfully fetched user data for userId: {}", userId);
            return Optional.of(userData);

        } catch (Exception e) {
            log.error("Error fetching user from Keycloak with userId: {}", userId, e);
            return Optional.empty();
        }
    }

    /**
     * Mapuje UserRepresentation na KeycloakUserData
     */
    private KeycloakUserData mapToUserData(UserRepresentation user) {
        Map<String, List<String>> attributes = user.getAttributes();

        String nip = null;
        if (attributes != null && attributes.containsKey("NIP")) {
            List<String> nipList = attributes.get("NIP");
            if (nipList != null && !nipList.isEmpty()) {
                nip = nipList.getFirst();
            }
        }

        return KeycloakUserData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .emailVerified(user.isEmailVerified())
                .nip(nip)
                .build();
    }

    /**
     * Odświeża token Keycloak Admin Client
     * Zapobiega błędom 401 Unauthorized gdy token wygaśnie
     */
    private void refreshToken() {
        try {
            // Wywołaj tokenManager().getAccessToken() aby wymusić odświeżenie
            keycloakCustomer.tokenManager().getAccessToken();
            log.trace("Keycloak token refreshed successfully");
        } catch (Exception e) {
            log.warn("Failed to refresh Keycloak token, will retry on next request", e);
        }
    }
}
