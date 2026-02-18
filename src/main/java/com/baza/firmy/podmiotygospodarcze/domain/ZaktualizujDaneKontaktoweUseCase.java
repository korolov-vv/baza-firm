package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class ZaktualizujDaneKontaktoweUseCase {

  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;

  @Transactional
  public UUID zaktualizujDaneKontaktowe(FirmaPortalZewnDto firmaPortalZewnDto) {
    if (firmaPortalZewnDto.getNip().isEmpty()) {
      log.info("ZaktualizujDaneKontaktoweUseCase zaktualizujDaneKontaktowe(): Brak NIP dla firmy: {}",
          firmaPortalZewnDto.getNazwa());
      return null;
    }

    boolean czyAktualizowac = false;

    PodmiotGospodarczyEntity toUpdate =
        podmiotyGospodarczeRepository.findFirstByNipOrderByDataRozpoczeciaDesc(firmaPortalZewnDto.getNip().get())
            .orElseThrow(() -> new IllegalArgumentException(
                String.format("Podmiot gospodarczy o podanym numerze NIP: %s nie istnieje",
                    firmaPortalZewnDto.getNip().get())));

    if (firmaPortalZewnDto.getEmail().isPresent() && Objects.isNull(toUpdate.getEmail())) {
      toUpdate.setEmail(firmaPortalZewnDto.getEmail().get());
      czyAktualizowac = true;
    }

    if (firmaPortalZewnDto.getTelefon().isPresent() && Objects.isNull(toUpdate.getTelefon())) {
      toUpdate.setTelefon(firmaPortalZewnDto.getTelefon().get());
      czyAktualizowac = true;
    }

    if (firmaPortalZewnDto.getStronaWww().isPresent() &&
        Objects.isNull(toUpdate.getWww())) {
      toUpdate.setWww(firmaPortalZewnDto.getStronaWww().get());
      czyAktualizowac = true;
    }

    if (!czyAktualizowac) {
      log.info("ZaktualizujDaneKontaktoweUseCase zaktualizujDaneKontaktowe(): Brak zmian do aktualizacji dla firmy: {}",
          firmaPortalZewnDto.getNazwa());
      return null;
    }
    return podmiotyGospodarczeRepository.save(toUpdate).getUuid();
  }
}
