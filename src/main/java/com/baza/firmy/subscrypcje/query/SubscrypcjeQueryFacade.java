package com.baza.firmy.subscrypcje.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import com.baza.firmy.subscrypcje.domain.dto.SubscrypcjaUzytkownikaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscrypcjeQueryFacade {

  private final SubscrypcjeQueryRepository subscrypcjeQueryRepository;
  private final UzytkownicySubscrypcjeQueryRepository uzytkownicySubscrypcjeQueryRepository;

  public Optional<SubscrypcjaViewEntity> findByUuid(UUID uuid) {
    return subscrypcjeQueryRepository.findByUuid(uuid);
  }

  public Optional<SubscrypcjaViewEntity> findByNazwa(String email) {
      return subscrypcjeQueryRepository.findByNazwa(email);
  }

  public Optional<SubscrypcjaUzytkownikaDto> znajdzAktywnaSubscrypcjeUzytkownika(UUID uuidUzytkownika) {
    return uzytkownicySubscrypcjeQueryRepository.findByUzytkownikUuidAndStatusSubscrypcji(uuidUzytkownika, StatusSubscrypcji.AKTYWNA)
            .map(uzytkownikSubscrypcja -> SubscrypcjaUzytkownikaDto.builder()
                    .uuid(uzytkownikSubscrypcja.getUuid())
                    .nazwa(uzytkownikSubscrypcja.getSubscrypcja().getNazwa())
                    .opis(uzytkownikSubscrypcja.getSubscrypcja().getOpis())
                    .iloscDostepnychFirm(uzytkownikSubscrypcja.getSubscrypcja().getIloscDostepnychFirm())
                    .aktywnaOd(uzytkownikSubscrypcja.getAktywnaOd())
                    .aktywnaDo(uzytkownikSubscrypcja.getAktywnaDo())
                    .statusSubscrypcji(uzytkownikSubscrypcja.getStatusSubscrypcji())
                    .build());
  }
}
