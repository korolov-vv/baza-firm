package com.baza.firmy.common.service;

import com.baza.firmy.configuration.properties.KrsProperties;
import com.baza.firmy.integration.krs.KrsClient;
import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
