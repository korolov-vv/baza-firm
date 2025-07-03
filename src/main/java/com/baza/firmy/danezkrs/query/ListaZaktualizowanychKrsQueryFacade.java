package com.baza.firmy.danezkrs.query;


import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.dto.ListaZmienionychWpisowDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListaZaktualizowanychKrsQueryFacade {

  private final ListaZaktualizowanychWpisowKrsQueryRepository repository;

  public boolean czyIstniejeListaZaktualizowanychWpisowWTrakciePobierania() {
    return repository.existsByStatusPobierania(StatusPobieraniaEnum.W_TRAKCIE);
  }

  public ListaZmienionychWpisowDto pobierzNieobsluzanaListeZaktualizowanychWpisow() {
    return repository.findFirstByCzyObsluzonaIsFalse()
        .map(lista -> ListaZmienionychWpisowDto.builder()
            .uuid(lista.getUuid())
            .identyfikatoryWpisow(
                lista.getNumeryKrs() != null ? lista.getNumeryKrs() : List.of())
            .build())
        .orElseThrow(() -> new RuntimeException("Brak nieobsłużonej listy zaktualizowanych wpisów KRS"));
  }
}
