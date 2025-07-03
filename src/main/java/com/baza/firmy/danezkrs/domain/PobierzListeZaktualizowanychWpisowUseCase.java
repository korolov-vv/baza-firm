package com.baza.firmy.danezkrs.domain;

import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class PobierzListeZaktualizowanychWpisowUseCase {

  private final ListaZaktualizowanychWpisowKrsMapper mapper;
  private final ListaZaktualizowanychWpisowKrsRepository repository;

  public UUID pobierzOrazZapiszListeZaktualizowanychWpisow(ListaZmienionychWpisowKrsResponse dto) {
    return repository.save(mapper.toEntity(dto)).getUuid();
  }
}
