package com.baza.firmy.danezkrs.domain;


import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListaZaktualizowanychKrsFacade {

  private final PobierzListeZaktualizowanychWpisowUseCase pobierzListeZaktualizowanychWpisowUseCase;
  private final ZmienStatusListyWpisowUseCase zmienStatusListyWpisowUseCase;

  public void zapiszListeZaktualizowanychWpisow(ListaZmienionychWpisowKrsResponse dto) {
    pobierzListeZaktualizowanychWpisowUseCase.pobierzOrazZapiszListeZaktualizowanychWpisow(dto);
  }

  public void zmienStatusListyWpisow(UUID uuid, StatusPobieraniaEnum status) {
    zmienStatusListyWpisowUseCase.zmienStatusListyWpisowUseCase(uuid, status);
  }
  public void zmienStatusListyWpisow(UUID uuid, StatusPobieraniaEnum status, List<String> listaNiepobranychWpisow) {
    zmienStatusListyWpisowUseCase.zmienStatusListyWpisowUseCase(uuid, status, listaNiepobranychWpisow);
  }
}
