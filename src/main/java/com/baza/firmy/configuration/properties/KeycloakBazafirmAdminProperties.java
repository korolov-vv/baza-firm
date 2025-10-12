package com.baza.firmy.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak-admin")
public class KeycloakBazafirmAdminProperties extends KeycloakProperties {

}
