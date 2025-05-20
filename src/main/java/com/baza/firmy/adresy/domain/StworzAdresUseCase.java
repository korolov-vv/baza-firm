package com.baza.firmy.adresy.domain;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StworzAdresUseCase {

  private final AdresRepository adresRepository;
  private final AdresMapper adresMapper;

  public UUID stworzAdres(AdresDto adresDto) {
    if (adresDto == null) {
      return null;
    }
    AdresEntity adresEntity = adresMapper.toAdresEntity(adresDto);
    return adresRepository.save(adresEntity).getUuid();
  }
}
