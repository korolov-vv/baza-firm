package com.baza.firmy.common.service;

import com.baza.firmy.integration.ceidg.CeidgClient;
import com.baza.firmy.response.Dto;
import com.baza.firmy.response.ListaJdgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class CeidgService {
  
  private final CeidgClient ceidgClient;

  public ListaJdgDto pobierzListeJdg(String link) {
    return ceidgClient.pobierzListeJdg(link);
  }

  public Dto pobierzSzczegolyJdg(String link) {
    final var response = ceidgClient.pobierzSzczegolyJdg(link);
    log.info("Pobrano szczegóły JDG link: {},\n response: {}", link, response);
    return response;
  }
}
