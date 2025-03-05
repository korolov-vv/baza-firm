package com.baza.firmy.service;

import com.baza.firmy.integration.ceidg.CeidgClient;
import com.baza.firmy.response.Dto;
import com.baza.firmy.response.ListaJdgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CeidgService {
  
  private final CeidgClient ceidgClient;

  public ListaJdgDto pobierzListeJdg(String link) {
    return ceidgClient.pobierzListeJdg(link);
  }

  public Dto pobierzSzczegolyJdg(String link) {
    return ceidgClient.pobierzSzczegolyJdg(link);
  }
}
