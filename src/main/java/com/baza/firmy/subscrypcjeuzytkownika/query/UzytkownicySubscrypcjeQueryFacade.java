package com.baza.firmy.subscrypcjeuzytkownika.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import com.baza.firmy.subscrypcjeuzytkownika.dto.SubscrypcjaUzytkownikaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UzytkownicySubscrypcjeQueryFacade {

    private final UzytkownicySubscrypcjeQueryRepository uzytkownicySubscrypcjeQueryRepository;

    public Optional<SubscrypcjaUzytkownikaDto> znajdzAktywnaSubscrypcjeDlaFirmy(UUID uuidFirmyKlienta) {
        List<UzytkownikSubscrypcjaViewEntity> listaAktywnychSubscrypcji = uzytkownicySubscrypcjeQueryRepository.findAllByFirmaKlientaUuidAndStatusSubscrypcjiOrderByAktywnaDoDesc(uuidFirmyKlienta, StatusSubscrypcji.AKTYWNA);

        if (listaAktywnychSubscrypcji.size() > 1) {
            log.warn("Znaleziono więcej niż jedną aktywną subskrypcję dla firmy o UUID: {}", uuidFirmyKlienta);
        }
        return listaAktywnychSubscrypcji.stream()
                .findFirst()
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
