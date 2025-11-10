package com.baza.firmy.common.client;

import com.baza.firmy.configuration.properties.KeycloakBazafirmAdminProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class KeycloakAuthAdminClient extends KeycloakAuthClient {

  public KeycloakAuthAdminClient(WebClient webClient,
                                 KeycloakBazafirmAdminProperties keycloakProperties) {
    super(webClient, keycloakProperties);
  }
}
