package com.baza.firmy.kraje.domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StworzKrajeUseCase {

  private final KrajRepository krajRepository;
  private final KrajMapper krajMapper;

  public List<UUID> stworzKraje(List<KrajDto> krajDto) {
    return krajDto.stream()
        .filter(kraj -> Objects.nonNull(kraj.getKraj()))
        .map(kraj -> krajRepository.findByKraj(kraj.getKraj().trim())
            .orElseGet(() -> krajRepository.save(krajMapper.toKrajEntity(kraj))))
        .map(KrajEntity::getUuid)
        .toList();
  }
}
