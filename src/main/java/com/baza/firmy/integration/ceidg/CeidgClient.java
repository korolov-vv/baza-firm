package com.baza.firmy.integration.ceidg;

import static com.baza.firmy.constants.WebClientConstants.CALL_TO_CEIDG_FAILED_LOG;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.baza.firmy.configuration.properties.CeidgProperties;
import com.baza.firmy.dto.ListaZmienionychWpisowDto;
import com.baza.firmy.response.Dto;
import com.baza.firmy.response.ListaJdgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CeidgClient {

  public static final String START_REQUEST_LOG = "----------------- Begin the request to CEIDG {} -------------------------";
  public static final String COMPLETE_REQUEST_LOG = "----------------- Complete the request to CEIDG {} -------------------------";

  private final WebClient webClient;
  private final CeidgProperties ceidgProperties;

  public ListaJdgDto pobierzListeJdg(String link) {
    log.info(START_REQUEST_LOG, link);
    final var response =
        webClient
            .get()
            .uri(link)
            .header(AUTHORIZATION, createAuthorizationHeader())
            .retrieve()
            .bodyToMono(ListaJdgDto.class)
            .onErrorResume(
                throwable -> {
                  log.error(CALL_TO_CEIDG_FAILED_LOG, throwable.getMessage());
                  return Mono.error(throwable);
                })
            .block();
    log.info(COMPLETE_REQUEST_LOG, link);
    return response;
  }

  public ListaZmienionychWpisowDto pobierzListeZmienionychWpisow(String link) {
    log.info(START_REQUEST_LOG, link);
    final var response =
        webClient
            .get()
            .uri(link)
            .header(AUTHORIZATION, createAuthorizationHeader())
            .retrieve()
            .bodyToMono(ListaZmienionychWpisowDto.class)
            .onErrorResume(
                throwable -> {
                  log.error(CALL_TO_CEIDG_FAILED_LOG, throwable.getMessage());
                  return Mono.error(throwable);
                })
            .block();
    log.info(COMPLETE_REQUEST_LOG, link);
    return response;
  }

  public Dto pobierzSzczegolyJdg(String link) {
    log.info(START_REQUEST_LOG, link);
    final var response =
        webClient
            .get()
            .uri(link)
            .header(AUTHORIZATION, createAuthorizationHeader())
            .retrieve()
            .bodyToMono(Dto.class)
            .onErrorResume(
                throwable -> {
                  log.error(CALL_TO_CEIDG_FAILED_LOG, throwable.getMessage());
                  return Mono.error(throwable);
                })
            .block();
    log.info(COMPLETE_REQUEST_LOG, link);
    return response;
  }

  private String createAuthorizationHeader() {
    return "Bearer " + ceidgProperties.getToken();
  }
}
