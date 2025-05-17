package com.baza.firmy.osoby.domain;

import com.baza.firmy.kraje.domain.KrajFacade;
import com.baza.firmy.kraje.query.KrajQueryFacade;
import com.baza.firmy.osoby.domain.dto.StworzWlascicielaDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StworzOsobeUseCase {

  private final KrajFacade krajFacade;
  private final KrajQueryFacade krajQueryFacade;
  private final OsobaRepository osobaRepository;
  private final OsobaMapper osobaMapper;

  public UUID stworzOsobe(StworzWlascicielaDto wlascicielDto) {
    List<UUID> listaUuidKrajeZapisane = new ArrayList<>();

    if (wlascicielDto.getObywatelstwa() != null && !wlascicielDto.getObywatelstwa().isEmpty()) {
      listaUuidKrajeZapisane.addAll(krajFacade.stworzKraje(wlascicielDto.getObywatelstwa()));
    }

    OsobaEntity osobaEntity = osobaMapper.toOsobaEntity(wlascicielDto);
    osobaEntity.setObywatelstwa(krajQueryFacade.getKrajePoUuidList(listaUuidKrajeZapisane));
    return osobaRepository.save(osobaEntity).getUuid();
  }
}
