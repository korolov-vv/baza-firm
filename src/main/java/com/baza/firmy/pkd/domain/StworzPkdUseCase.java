package com.baza.firmy.pkd.domain;

import com.baza.firmy.pkd.domain.dto.PkdDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StworzPkdUseCase {

  private final PkdRepository pkdRepository;
  private final PkdMapper pkdMapper;

  public UUID stworzPkd(PkdDto pkdDto) {
    if (pkdDto == null || pkdDto.getKod() == null) {
      return null;
    }
    return pkdRepository.save(pkdMapper.toPkdEntity(pkdDto)).getUuid();
  }
}
