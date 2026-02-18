package com.baza.firmy.common.service;

import com.baza.firmy.configuration.properties.KrsProperties;
import com.baza.firmy.integration.krs.KrsClient;
import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class KrsService {

  private final KrsProperties krsProperties;
  private final KrsClient krsClient;

  public ListaZmienionychWpisowKrsResponse pobierzListeZmienionychWpisow(LocalDate data, int godzinaOd, int godzinaDo) {
    log.info("Pobieranie listy zaktualizowanych wpisów KRS dla daty: {}, godzinaOd: {}, godzinaDo: {}", data, godzinaOd, godzinaDo);
    final var response = krsClient.pobierzListeZmienionychWpisow(zwrocLinkDoListyZmienionychWpisow(data, godzinaOd, godzinaDo));

    log.info("Pobrano {} wpisów z KRS", response.numeryKrs().size());
    return response;
  }

  @Async("pobierzOdpisAktualny")
  public CompletableFuture<OdpisAktualnyResponse> pobierzOdpisAktualny(String nrKrs) {
    log.info("Pobieranie odpisu aktualnego KRS dla numeru KRS: {}", nrKrs);
    final var link = UriComponentsBuilder.fromUriString(krsProperties.getKrsPath())
        .path("/OdpisAktualny")
        .path("/" + nrKrs)
        .toUriString();

    OdpisAktualnyResponse response;
    try {
      response = krsClient.pobierzOdpisAktualny(link);
      log.info("Pobrano odpis aktualny KRS dla numeru KRS: {},\n response: {}", nrKrs, response);
    } catch (WebClientException e) {
      if (e.getMessage().contains("404 Not Found")) {
        log.warn("Nie znaleziono odpisu aktualnego KRS dla numeru KRS: {}", nrKrs);
      } else {
        log.error("Błąd podczas pobierania odpisu aktualnego KRS dla numeru KRS: {}", nrKrs, e);
      }
      return CompletableFuture.completedFuture(null);
    }
    return CompletableFuture.completedFuture(response);
  }
  private String zwrocLinkDoListyZmienionychWpisow(LocalDate data, int godzinaOd, int godzinaDo) {
    return UriComponentsBuilder.fromUriString(krsProperties.getKrsPath())
        .path("/Biuletyn")
        .path("/" + data)
        .queryParams(przygotujParametry(godzinaOd, godzinaDo))
        .toUriString();
  }

  private MultiValueMap<String, String> przygotujParametry(int godzinaOd, int godzinaDo) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

    multiValueMap.add("godzinaOd", String.valueOf(godzinaOd));
    multiValueMap.add("godzinaDo", String.valueOf(godzinaDo));

    return multiValueMap;
  }
}
