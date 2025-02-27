package com.baza.firmy.service;

import com.baza.firmy.integration.ceidg.CeidgClient;
import com.baza.firmy.response.ListaJdgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CeidgService {
  
  private final CeidgClient ceidgClient;

  public ListaJdgDto getBusineses(String link) {
    return ceidgClient.getBusineses(link);
  }
}
