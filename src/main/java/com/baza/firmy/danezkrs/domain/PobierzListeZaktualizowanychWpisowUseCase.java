package com.baza.firmy.danezkrs.domain;

import com.baza.firmy.response.krs.ListaZmienionychWpisowKrsResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class PobierzListeZaktualizowanychWpisowUseCase {

  private final ListaZaktualizowanychWpisowKrsMapper mapper;
  private final ListaZaktualizowanychWpisowKrsRepository repository;

  @Transactional
  public UUID pobierzOrazZapiszListeZaktualizowanychWpisow(ListaZmienionychWpisowKrsResponse dto) {
    return repository.save(mapper.toEntity(dto)).getUuid();
  }
}
