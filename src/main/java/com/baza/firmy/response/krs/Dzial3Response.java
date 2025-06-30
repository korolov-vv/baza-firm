package com.baza.firmy.response.krs;

import java.util.List;
import lombok.Builder;

@Builder
public record Dzial3Response(
    PrzedmiotDzialalnosciResponse przedmiotDzialalnosci,
    WzmiankiOZlozonychDokumentachResponse wzmiankiOZlozonychDokumentach,
    InformacjaODniuKonczacymRokObrotowyResponse informacjaODniuKonczacymRokObrotowy
) {

  @Builder
  public record PrzedmiotDzialalnosciResponse(
      List<KodPkdResponse> przedmiotPrzewazajacejDzialalnosci,
      List<KodPkdResponse> przedmiotPozostalejDzialalnosci
  ) {}

  @Builder
  public record KodPkdResponse(
      String opis,
      String kodDzial,
      String kodKlasa,
      String kodPodklasa
  ) {}

  @Builder
  public record WzmiankiOZlozonychDokumentachResponse(
      List<WzmiankaOZlozeniuRocznegoSprawozdaniaFinansowegoResponse> wzmiankaOZlozeniuRocznegoSprawozdaniaFinansowego,
      List<WzmiankaZaOkresResponse> wzmiankaOZlozeniuUchwalyPostanowieniaOZatwierdzeniuRocznegoSprawozdaniaFinansowego,
      List<WzmiankaZaOkresResponse> wzmiankaOZlozeniuSprawozdaniaZDzialalnosci
  ) {

    @Builder
    public record WzmiankaOZlozeniuRocznegoSprawozdaniaFinansowegoResponse(
        String dataZlozenia,
        String zaOkresOdDo
    ) {}

    @Builder
    public record WzmiankaZaOkresResponse(
        String zaOkresOdDo
    ) {}
  }

  @Builder
  public record InformacjaODniuKonczacymRokObrotowyResponse(
      String dzienKonczacyPierwszyRokObrotowy
  ) {}
}
