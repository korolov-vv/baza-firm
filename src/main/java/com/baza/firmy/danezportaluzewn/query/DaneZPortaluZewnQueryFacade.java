package com.baza.firmy.danezportaluzewn.query;


import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import com.baza.firmy.danezportaluzewn.query.dto.PortalZewnKategoriaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DaneZPortaluZewnQueryFacade {

  private final PortalZewnQueryRepository repository;
  private final PortalZewnKategoriaQueryRepository kategoriaRepository;
  private final PortalZewnKategoriaMapper kategoriaMapper;

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

  public boolean czyIstniejeKategoriaWTrakciePobierania() {
      return kategoriaRepository.existsByStatusPobierania(StatusPobieraniaEnum.W_TRAKCIE);
  }

  public Optional<PortalZewnKategoriaDto> pobierzPierwszaNiepodjetaKategorie() {
      return kategoriaRepository.findFirstByStatusPobierania(StatusPobieraniaEnum.NIEPODJETE)
              .map(kategoriaMapper::toDto);
  }
}
