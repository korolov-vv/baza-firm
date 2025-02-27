package com.baza.firmy.service;


import com.baza.firmy.configuration.properties.CeidgProperties;
import com.baza.firmy.entity.ListaJdgPobieranie;
import com.baza.firmy.response.ListaJdgDto;
import jakarta.annotation.Nullable;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PobierzDaneZCeidgService {

  private final CeidgService ceidgService;
  private final JednoosobowaDzialalnoscGospodarczaService jednoosobowaDzialalnoscGospodarczaService;
  private final CeidgProperties ceidgProperties;
  private final ListaJdgPobieranieService listaJdgPobieranieService;

  public void pobierzListyJdgWsteczIZapisz(@Nullable Map<String, String> params, @Nullable String link) {

    try {
      ListaJdgDto listaJdgDto = null;
      Optional<ListaJdgPobieranie> ostatniaPobranaStrona = listaJdgPobieranieService.znajdzOstatniaZapisanaListe();

      if (ostatniaPobranaStrona.isEmpty()) {
        listaJdgDto = ceidgService.getBusineses(link != null ? link : zwrocLinkDoListyFirm(params));
        listaJdgDto.setCzyStareDane(true);
      }

      if (ostatniaPobranaStrona.isPresent() && getStrona(ostatniaPobranaStrona.get().getSelf()) == 0) {
        listaJdgDto = ceidgService.getBusineses(ostatniaPobranaStrona.get().getLast());
        listaJdgDto.setCzyStareDane(true);
      }

      if (listaJdgDto != null) {
        listaJdgPobieranieService.zapisz(listaJdgDto);
      }

      long startTime = System.currentTimeMillis();

      do {
        listaJdgDto = pobierzIZapiszListeAktywnychDzialalnosci(null, listaJdgDto != null ? listaJdgDto.getLinks().getPrev() : ostatniaPobranaStrona.get().getPrev());
        listaJdgDto.setCzyStareDane(true);
        if (((new Date()).getTime() - startTime) < 3600) {
          Thread.sleep(3600 - (new Date()).getTime() - startTime);
        }
        if (listaJdgDto != null) {
          listaJdgPobieranieService.zapisz(listaJdgDto);
        }
        startTime = System.currentTimeMillis();
      } while ((getStrona(listaJdgDto.getLinks().getPrev()) > 0));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void pobierzListyJdgOstDobaIZapisz(@Nullable Map<String, String> params, @Nullable String link) {

    try {
      ListaJdgDto listaJdgDto = ceidgService.getBusineses(link != null ? link : zwrocLinkDoListyFirm(params));

      if (listaJdgDto != null) {
        listaJdgPobieranieService.zapisz(listaJdgDto);
      }

      long startTime = System.currentTimeMillis();

      while ((getStrona(listaJdgDto.getLinks().getPrev()) > 0)) {
        listaJdgDto = pobierzIZapiszListeAktywnychDzialalnosci(null, listaJdgDto.getLinks().getNext());
        if (((new Date()).getTime() - startTime) < 3600) {
          Thread.sleep(3600 - (new Date()).getTime() - startTime);
        }
        if (listaJdgDto != null) {
          listaJdgPobieranieService.zapisz(listaJdgDto);
        }
        startTime = System.currentTimeMillis();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ListaJdgDto pobierzIZapiszListeAktywnychDzialalnosci(@Nullable Map<String, String> params, @Nullable String link) {
    ListaJdgDto listaJdgDto = ceidgService.getBusineses(link != null ? link : zwrocLinkDoListyFirm(params));
    jednoosobowaDzialalnoscGospodarczaService.saveBusinesses(listaJdgDto.getFirmy());
    return listaJdgDto;
  }

  private int getStrona(String link) {
    String[] split = link.split("=");
    return Integer.parseInt(split[split.length - 1]);
  }

  private String zwrocLinkDoListyFirm(Map<String, String> params) {
    String link = ceidgProperties.getCeidgPath();
    if (params != null && !params.isEmpty()) {
      link = UriComponentsBuilder.fromUriString(ceidgProperties.getCeidgPath())
          .path("/firmy")
          .queryParams(przygotujParametry(params))
          .toUriString();
    }
    return link;
  }

  private MultiValueMap<String, String> przygotujParametry(Map<String, String> params) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

    if (params != null) {
      params.forEach(multiValueMap::add);
    }

    return multiValueMap;
  }
}
