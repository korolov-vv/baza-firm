package com.baza.firmy.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak-customer")
public class KeycloakBazafirmCustomerProperties extends KeycloakProperties {

}
