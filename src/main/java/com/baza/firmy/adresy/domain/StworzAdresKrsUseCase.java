package com.baza.firmy.adresy.domain;

import com.baza.firmy.response.krs.SiedzibaIAdresResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class StworzAdresKrsUseCase {

  private final AdresRepository adresRepository;
  private final AdresMapper adresMapper;

  public UUID stworzAdres(SiedzibaIAdresResponse.AdresKrsResponse adresKrsResponse) {
    if (adresKrsResponse == null) {
      return null;
    }
    AdresEntity adresEntity = adresMapper.toAdresEntity(adresKrsResponse);
    return adresRepository.save(adresEntity).getUuid();
  }
}
