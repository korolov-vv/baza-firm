package com.baza.firmy.danezkrs.domain;


import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListaZaktualizowanychKrsFacade {

  private final PobierzListeZaktualizowanychWpisowUseCase pobierzListeZaktualizowanychWpisowUseCase;

  public void pobierzOrazZapiszListeZaktualizowanychWpisow(LocalDate data, int godzinaOd, int godzinaDo) {
    pobierzListeZaktualizowanychWpisowUseCase.pobierzOrazZapiszListeZaktualizowanychWpisow(data, godzinaOd, godzinaDo);
  }
}
