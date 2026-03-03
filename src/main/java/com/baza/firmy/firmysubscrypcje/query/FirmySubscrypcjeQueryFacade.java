package com.baza.firmy.firmysubscrypcje.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import com.baza.firmy.firmysubscrypcje.dto.ParametrySubscrypcjiDto;
import com.baza.firmy.firmysubscrypcje.dto.FirmaSubscrypcjaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirmySubscrypcjeQueryFacade {

    private final FirmySubscrypcjeQueryRepository firmySubscrypcjeQueryRepository;

    public Optional<FirmaSubscrypcjaDto> znajdzAktywnaSubscrypcjeDlaFirmy(UUID uuidFirmyKlienta) {
        List<FirmaSubscrypcjaViewEntity> listaAktywnychSubscrypcji = firmySubscrypcjeQueryRepository.findAllByFirmaKlientaUuidAndStatusSubscrypcjiOrderByAktywnaDoDesc(uuidFirmyKlienta, StatusSubscrypcji.AKTYWNA);

        if (listaAktywnychSubscrypcji.size() > 1) {
            log.warn("Znaleziono więcej niż jedną aktywną subskrypcję dla firmy o UUID: {}", uuidFirmyKlienta);
        }
        return listaAktywnychSubscrypcji.stream()
                .findFirst()
                .map(firmaSubscrypcja -> FirmaSubscrypcjaDto.builder()
                        .uuid(firmaSubscrypcja.getUuid())
                        .uuidFirmyKlienta(firmaSubscrypcja.getFirmaKlienta().getUuid())
                        .nazwa(firmaSubscrypcja.getSubscrypcja().getNazwa())
                        .opis(firmaSubscrypcja.getSubscrypcja().getOpis())
                        .iloscDostepnychFirm(firmaSubscrypcja.getSubscrypcja().getIloscDostepnychFirm())
                        .aktywnaOd(firmaSubscrypcja.getAktywnaOd())
                        .aktywnaDo(firmaSubscrypcja.getAktywnaDo())
                        .statusSubscrypcji(firmaSubscrypcja.getStatusSubscrypcji())
                        .parametrySubscrypcji(stworzParametrySubscrypcjiDto(firmaSubscrypcja.getParametrySubscrypcji()))
                        .build());
    }

    private ParametrySubscrypcjiDto stworzParametrySubscrypcjiDto(ParametrySubscrypcjiViewEntity parametrySubscrypcji) {
        if (parametrySubscrypcji == null) {
            return null;
        }
        return ParametrySubscrypcjiDto.builder()
                .uuid(parametrySubscrypcji.getUuid())
                .pkd(parametrySubscrypcji.getPkd().orElse(null))
                .dataRozpoczeciaOd(parametrySubscrypcji.getDataRozpoczeciaOd().orElse(null))
                .dataRozpoczeciaDo(parametrySubscrypcji.getDataRozpoczeciaDo().orElse(null))
                .wojewodztwo(parametrySubscrypcji.getWojewodztwo().orElse(null))
                .powiat(parametrySubscrypcji.getPowiat().orElse(null))
                .gmina(parametrySubscrypcji.getGmina().orElse(null))
                .build();
    }

    public Optional<FirmaSubscrypcjaViewEntity> findByUuidPelneInfo(UUID uuid) {
        return firmySubscrypcjeQueryRepository.findByUuidPelneInfo(uuid);
    }

    public List<FirmaSubscrypcjaViewEntity> findAll() {
        return firmySubscrypcjeQueryRepository.findAll();
    }
}
