package com.baza.firmy.common.client;

import com.baza.firmy.configuration.properties.KeycloakProperties;
import com.baza.firmy.request.LoginRequest;
import com.baza.firmy.request.LogoutRequest;
import com.baza.firmy.request.RefreshTokenRequest;
import com.baza.firmy.response.KeycloakToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import static org.keycloak.OAuth2Constants.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Slf4j
@RequiredArgsConstructor
public class KeycloakAuthClient {

  private final WebClient webClient;
  private final KeycloakProperties keycloakProperties;

  public KeycloakToken generateToken(LoginRequest loginRequest) {
    final var form = new LinkedMultiValueMap<String, String>();
    form.add(GRANT_TYPE, PASSWORD);
    form.add(USERNAME, loginRequest.email());
    form.add(PASSWORD, loginRequest.password());
    log.info("form username + password: {}, {}", form.getFirst(USERNAME), form.getFirst(PASSWORD));
    return makeAuthCall(form);
  }

  public KeycloakToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
    final var form = new LinkedMultiValueMap<String, String>();
    form.add(GRANT_TYPE, REFRESH_TOKEN);
    form.add(REFRESH_TOKEN, refreshTokenRequest.refreshToken());
    return makeAuthCall(form);
  }

  private LinkedMultiValueMap<String, String> getForm(String token) {
    final var form = new LinkedMultiValueMap<String, String>();
    form.add(GRANT_TYPE, TOKEN_EXCHANGE_GRANT_TYPE);
    form.add(SUBJECT_TOKEN, token);
    form.add(REQUESTED_TOKEN_TYPE, REFRESH_TOKEN_TYPE);
    return form;
  }

  public void logoutUser(LogoutRequest request) {
    final var client = keycloakProperties.getClients().iterator().next();
    final var form = new LinkedMultiValueMap<String, String>();
    form.add(REFRESH_TOKEN, request.refreshToken());
    form.add(CLIENT_ID, client.getClientId());
    form.add(CLIENT_SECRET, client.getClientSecret());
    webClient.post()
        .uri(keycloakProperties.getLogoutUrl())
        .bodyValue(form)
        .headers(httpHeaders -> {
          httpHeaders.setBearerAuth(request.accessToken());
          httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);
        })
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  private KeycloakToken makeAuthCall(MultiValueMap<String, String> credentials) {

    final var form = new LinkedMultiValueMap<String, String>();
    final var client = keycloakProperties.getClients().iterator().next();
    form.add(CLIENT_ID, client.getClientId());
    form.add(CLIENT_SECRET, client.getClientSecret());
    log.info("form client + secret: {}, {}", form.getFirst(CLIENT_ID), form.getFirst(CLIENT_SECRET));
    form.addAll(credentials);
    log.info("----------------- Begin request to Keycloak ------------------------- {} form: {}",
        keycloakProperties.getAuthUrl(), form);
    final var response = webClient.post()
        .uri(keycloakProperties.getAuthUrl())
        .bodyValue(form)
        .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED.toString())
        .retrieve()
        .bodyToMono(KeycloakToken.class)
        .block();
    log.info("----------------- Complete request to Keycloak -------------------------");
    return response;
  }

}
