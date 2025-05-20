package com.baza.firmy.common.service;


import com.baza.firmy.configuration.properties.CeidgProperties;
import com.baza.firmy.entity.ListaJdgPobieranie;
import com.baza.firmy.jdg.domain.JdgFacade;
import com.baza.firmy.jdg.query.JdgQueryFacade;
import com.baza.firmy.response.CeidgListDto;
import com.baza.firmy.response.Dto;
import com.baza.firmy.response.ListaJdgDto;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class PobierzDaneZCeidgService {

  private final JdgQueryFacade jdgQueryFacade;
  private final CeidgService ceidgService;
  private final JdgFacade jdgFacade;
  private final CeidgProperties ceidgProperties;
  private final ListaJdgPobieranieService listaJdgPobieranieService;

  public void pobierzListyJdgWsteczIZapisz(@Nullable Map<String, String> params, @Nullable String link) {
    try {
      ListaJdgDto listaJdgDto = null;
      Optional<ListaJdgPobieranie> ostatniaPobranaStrona = listaJdgPobieranieService.znajdzOstatniaZapisanaListe(true);
      if (ostatniaPobranaStrona == null) {
        listaJdgDto = pobierzPierwszaStrone(params, link, ostatniaPobranaStrona, listaJdgDto);
        listaJdgDto = pobierzOstatnaStrone(ostatniaPobranaStrona, listaJdgDto);
        zapiszStrone(listaJdgDto);
      }
      pobierajWstecz(listaJdgDto, ostatniaPobranaStrona);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void pobierzListyJdgNoweIZapisz(@Nullable Map<String, String> params, @Nullable String link) {
    try {
      ListaJdgDto listaJdgDto = ceidgService.pobierzListeJdg(link != null ? link : zwrocLinkDoListyFirm(params));
      zapiszStrone(listaJdgDto);
      pobierajNastepne(listaJdgDto);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public void pobierajSzczegolyNowychJdg() {
    log.info("Zaczynam pobieranie szczegolow nowych jdg");

    ListaJdgPobieranie lista;

    do {
      lista = listaJdgPobieranieService.pobierzNieobsluzonaListeNowa();
      if (lista != null) {
        obsluzListeJdg(lista);
      }
    } while (lista != null && LocalDateTime.now().getHour() != 0);

    log.info("Skończono pobieranie szczegolow nowych jdg");
  }

  @Transactional
  public void pobierajSzczegolyStareDaneJdg() {
    log.info("Zaczynam pobieranie szczegolow starych jdg");
    ListaJdgPobieranie lista;

    do {
      lista = listaJdgPobieranieService.pobierzNieobsluzonaListeStareDane();
      if (lista != null) {
        obsluzListeJdg(lista);
      }
    } while (lista != null && LocalDateTime.now().getHour() != 0);
    log.info("Skończono pobieranie szczegolow starych jdg");
  }

  public void pobierzBrakujaceDane() {
    jdgQueryFacade.pobierzLinkiDoJdgBezNipow().forEach(link -> {
      AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
        try {
          zatrzymajJesliKrocejNiz4000(startTime.get());
          Dto szczegolyDto = ceidgService.pobierzSzczegolyJdg(link);
          startTime.set(System.currentTimeMillis());

          if (szczegolyDto != null) {
            szczegolyDto.getFirma().forEach(jdgFacade::stworzJdg);
          } else {
            log.info("szczegolyDto dla {} is NULL", link);
          }
        } catch (Exception e) {
          e.printStackTrace();
          startTime.set(System.currentTimeMillis());
        }
      });
  }

  private void obsluzListeJdg(ListaJdgPobieranie listaJdgPobieranie) {
    AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
    log.info("Started downloading the list {}", listaJdgPobieranie.getUuid());
    listaJdgPobieranie.getFirmy().forEach(pobierzDaneFirm(startTime));
    try {
      listaJdgPobieranie.setCzyObsluzona(true);
      listaJdgPobieranieService.zapisz(listaJdgPobieranie);
    } catch (Exception e) {
      log.error("Error while saving the list {}", listaJdgPobieranie.getUuid(), e);
    }
    log.info("Finished downloading the list {}", listaJdgPobieranie.getUuid());
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

  private Consumer<CeidgListDto> pobierzDaneFirm(AtomicLong startTime) {
    return firma -> {
      if (!jdgQueryFacade.czyIstniejePoCeidgId(firma.getCeidgId())) {
        try {
          zatrzymajJesliKrocejNiz4000(startTime.get());
          Dto szczegolyDto = ceidgService.pobierzSzczegolyJdg(firma.getLink());
          startTime.set(System.currentTimeMillis());

          if (szczegolyDto != null) {
            szczegolyDto.getFirma().forEach(dzialalnosc -> {
              if (!jdgQueryFacade.existsByWlascicielNipAndNazwaAndDataRozpoczecia(
                  dzialalnosc.getWlasciciel().getNip(), dzialalnosc.getNazwa(), LocalDate.parse(dzialalnosc.getDataRozpoczecia()))) {
                jdgFacade.stworzJdg(dzialalnosc);
              } else {
                // TODO dorobić aktualizację dla aktualizacji
                log.info("PobierzDaneCeidgService: pobierzDaneFirm(): JDG dla NIP: {}, nazwa: {} oraz dataRozpoczecia: {} już istnieje",
                    firma.getWlasciciel().getNip(), firma.getNazwa(), firma.getDataRozpoczecia());
              }
            });
          } else {
            log.info("szczegolyDto dla {} is NULL", firma.getCeidgId());
          }
        } catch (Exception e) {
          e.printStackTrace();
          startTime.set(System.currentTimeMillis());
        }
      } else {
        log.info("Skiped jdg {}", firma.getCeidgId());
      }
    };
  }

  private ListaJdgDto pobierzPierwszaStrone(Map<String, String> params, String link,
      Optional<ListaJdgPobieranie> ostatniaPobranaStrona, ListaJdgDto listaJdgDto) {
    if (ostatniaPobranaStrona.isEmpty()) {
      listaJdgDto = ceidgService.pobierzListeJdg(link != null ? link : zwrocLinkDoListyFirm(params));
      listaJdgDto.setCzyStareDane(true);
    }
    return listaJdgDto;
  }

  private ListaJdgDto pobierzOstatnaStrone(Optional<ListaJdgPobieranie> ostatniaPobranaStrona,
      ListaJdgDto listaJdgDto) {
    if (ostatniaPobranaStrona.isPresent() && getStrona(ostatniaPobranaStrona.get().getSelf()) == 0) {
      listaJdgDto = ceidgService.pobierzListeJdg(ostatniaPobranaStrona.get().getLast());
      listaJdgDto.setCzyStareDane(true);
    }
    return listaJdgDto;
  }

  private void zapiszStrone(ListaJdgDto listaJdgDto) {
    if (listaJdgDto != null) {
      listaJdgPobieranieService.zapisz(listaJdgDto);
    }
  }

  private void pobierajWstecz(ListaJdgDto listaJdgDto,
      Optional<ListaJdgPobieranie> ostatniaPobranaStrona) {
    long startTime = System.currentTimeMillis();
    do {
      listaJdgDto = ceidgService.pobierzListeJdg(
          listaJdgDto != null ? listaJdgDto.getLinks().getPrev() : ostatniaPobranaStrona.get().getPrev());
      listaJdgDto.setCzyStareDane(true);
      zapiszStrone(listaJdgDto);
      zatrzymajJesliKrocejNiz4000(startTime);
      startTime = System.currentTimeMillis();
    } while ((getStrona(listaJdgDto.getLinks().getPrev()) > 0) && LocalDateTime.now().getHour() != 0);
  }

  private void pobierajNastepne(ListaJdgDto listaJdgDto) {
    long startTime = System.currentTimeMillis();
    while ((getStrona(listaJdgDto.getLinks().getSelf()) != getStrona(listaJdgDto.getLinks().getLast())) && LocalDateTime.now().getHour() != 0) {
      listaJdgDto = ceidgService.pobierzListeJdg(listaJdgDto.getLinks().getNext());
      zapiszStrone(listaJdgDto);
      zatrzymajJesliKrocejNiz4000(startTime);
      startTime = System.currentTimeMillis();
    }
  }

  private void zatrzymajJesliKrocejNiz4000(long startTime) {
    long duration = new Date().getTime() - startTime;
    if (duration < 4000) {
      try {
        Thread.sleep(4000 - duration);
        log.info("Finish break {} miliseconds", duration);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
