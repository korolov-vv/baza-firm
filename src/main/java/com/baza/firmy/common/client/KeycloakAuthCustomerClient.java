package com.baza.firmy.common.client;

import com.baza.firmy.configuration.properties.KeycloakBazafirmCustomerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class KeycloakAuthCustomerClient extends KeycloakAuthClient {

  public KeycloakAuthCustomerClient(WebClient webClient,
                                    KeycloakBazafirmCustomerProperties keycloakProperties) {
    super(webClient, keycloakProperties);
  }
}
