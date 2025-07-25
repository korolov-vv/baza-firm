package com.baza.firmy.danezportaluzewn.domain;


import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DaneZPortaluZewnFacade {

  private final ZmienStatusDanychZPortaluZewnUseCase zmienStatusDanychZPortaluZewnUseCase;

  public void zmienStatusDanychZPortaluZewn(UUID uuid, StatusPobieraniaEnum status) {
    zmienStatusDanychZPortaluZewnUseCase.zmienStatusDanychZPortaluZewn(uuid, status);
  }
  public void zmienStatusDanychZPortaluZewn(UUID uuid, StatusPobieraniaEnum status, List<FirmaPortalZewnDto> listaNiepobranychWpisow) {
    zmienStatusDanychZPortaluZewnUseCase.zmienStatusListyWpisowUseCase(uuid, status, listaNiepobranychWpisow);
  }
}
