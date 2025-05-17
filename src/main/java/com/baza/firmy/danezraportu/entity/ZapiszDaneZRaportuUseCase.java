package com.baza.firmy.danezraportu.entity;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ZapiszDaneZRaportuUseCase {

  private final DaneZRaportuRepository daneZRaportuRepository;
  private final DaneZRaportuMapper daneZRaportuMapper;

  UUID zapiszDane(DaneZRaportuDto daneZRaportuDto) {
    DaneZRaportuEntity daneZRaportuEntity = daneZRaportuMapper.toEntity(daneZRaportuDto);
    return daneZRaportuRepository.save(daneZRaportuEntity).getUuid();
  }
}
