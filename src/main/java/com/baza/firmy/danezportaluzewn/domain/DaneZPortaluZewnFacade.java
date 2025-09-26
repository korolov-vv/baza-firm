package com.baza.firmy.danezportaluzewn.domain;


import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DaneZPortaluZewnFacade {

  private final ZmienStatusDanychZPortaluZewnUseCase zmienStatusDanychZPortaluZewnUseCase;
  private final ZmienStatusPobierzniaKategoriiUseCase zmienStatusPobierzniaKategoriiUseCase;
  private final ZapiszPortalZewnListUseCase zapiszPortalZewnListUseCase;

  public UUID zapiszDaneZPortaluZewn(DaneZPortaluZewnDto daneZPortaluZewnDto) {
    return zapiszPortalZewnListUseCase.zapisz(daneZPortaluZewnDto);
  }

  public void zmienStatusDanychZPortaluZewn(UUID uuid, StatusPobieraniaEnum status) {
    zmienStatusDanychZPortaluZewnUseCase.zmienStatusDanychZPortaluZewn(uuid, status);
  }

  public void zmienStatusDanychZPortaluZewn(UUID uuid, StatusPobieraniaEnum status, List<FirmaPortalZewnDto> listaNiepobranychWpisow) {
    zmienStatusDanychZPortaluZewnUseCase.zmienStatusListyWpisowUseCase(uuid, status, listaNiepobranychWpisow);
  }

  public void zmienStatusPobierzniaKategorii(UUID uuid, StatusPobieraniaEnum status) {
    zmienStatusPobierzniaKategoriiUseCase.zmienStatusPobierzniaKategorii(uuid, status);
  }

  public void zmienStatusPobierzniaKategorii(UUID uuid, StatusPobieraniaEnum status, String blad) {
    zmienStatusPobierzniaKategoriiUseCase.zmienStatusPobierzniaKategorii(uuid, status, blad);
  }
}
