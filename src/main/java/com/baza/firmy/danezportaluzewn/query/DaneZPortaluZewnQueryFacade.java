package com.baza.firmy.danezportaluzewn.query;


import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DaneZPortaluZewnQueryFacade {

  private final PortalZewnQueryRepository repository;

  public boolean czyIstniejaWpisyWTrakciePobierania() {
    return repository.existsByStatusPobierania(StatusPobieraniaEnum.W_TRAKCIE);
  }

  public DaneZPortaluZewnDto pobierzNiepodjetaListeWpisow() {
    return repository.findFirstByStatusPobieraniaOrderByCreateDateDesc(StatusPobieraniaEnum.NIEPODJETE)
        .map(lista -> DaneZPortaluZewnDto.builder()
            .uuid(lista.getUuid())
            .firmy(lista.getFirmy())
            .pageLink(lista.getPageLink())
            .nextPageLink(lista.getNextPageLink())
            .statusPobierania(lista.getStatusPobierania())
            .niepobraneFirmy(lista.getNiepobraneFirmy())
            .build())
        .orElse(null);
  }
}
