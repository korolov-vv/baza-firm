package com.baza.firmy.configuration;

import com.baza.firmy.configuration.properties.KeycloakBazafirmAdminProperties;
import com.baza.firmy.configuration.properties.KeycloakBazafirmCustomerProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {

    @Bean(name = "keycloakBazafirmAdmin")
    public Keycloak keycloakBazafirmAdmin(KeycloakBazafirmAdminProperties keycloakBazafirmAdminProperties) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakBazafirmAdminProperties.getServerUrl())
                .realm(keycloakBazafirmAdminProperties.getRealm())
                .clientId(keycloakBazafirmAdminProperties.getAdminClientId())
                .clientSecret(keycloakBazafirmAdminProperties.getAdminClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    @Bean(name = "keycloakBazafirmCustomer")
    public Keycloak keycloakBazafirmCustomer(KeycloakBazafirmCustomerProperties keycloakBazafirmCustomerProperties) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakBazafirmCustomerProperties.getServerUrl())
                .realm(keycloakBazafirmCustomerProperties.getRealm())
                .clientId(keycloakBazafirmCustomerProperties.getAdminClientId())
                .clientSecret(keycloakBazafirmCustomerProperties.getAdminClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

}
