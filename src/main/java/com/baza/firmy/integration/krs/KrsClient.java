package com.baza.firmy.integration.krs;

import static com.baza.firmy.constants.WebClientConstants.CALL_TO_CEIDG_FAILED_LOG;
import static com.baza.firmy.constants.WebClientConstants.CALL_TO_KRS_FAILED_LOG;

import com.baza.firmy.configuration.properties.CeidgProperties;
import com.baza.firmy.response.CeidgListDto;
import com.baza.firmy.response.Dto;
import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KrsClient {

  public static final String START_REQUEST_LOG = "----------------- Begin the request to KRS {} -------------------------";
  public static final String COMPLETE_REQUEST_LOG = "----------------- Complete the request to KRS {} -------------------------";

  private final WebClient webClient;
  private final CeidgProperties ceidgProperties;

  public ListaZmienionychWpisowKrsResponse pobierzListeZmienionychWpisow(String link) {
    log.info(START_REQUEST_LOG, link);
    final var response =
        webClient
            .get()
            .uri(link)
            .retrieve()
            .bodyToMono(ListaZmienionychWpisowKrsResponse.class)
            .onErrorResume(
                throwable -> {
                  log.error(CALL_TO_KRS_FAILED_LOG, throwable.getMessage());
                  return Mono.error(throwable);
                })
            .block();
    log.info(COMPLETE_REQUEST_LOG, link);
    return response;
  }

  public OdpisAktualnyResponse pobierzOdpisAktualny(String link) {
    log.info(START_REQUEST_LOG, link);
    final var response =
        webClient
            .get()
            .uri(link)
            .retrieve()
            .bodyToMono(OdpisAktualnyResponse.class)
            .onErrorResume(
                throwable -> {
                  log.error(CALL_TO_CEIDG_FAILED_LOG, throwable.getMessage());
                  return Mono.error(throwable);
                })
            .block();
    log.info(COMPLETE_REQUEST_LOG, link);
    return response;
  }
}
