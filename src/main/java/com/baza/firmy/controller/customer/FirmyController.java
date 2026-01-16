package com.baza.firmy.controller.customer;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.dto.PageResponseDto;
import com.baza.firmy.dto.UserPrincipal;
import com.baza.firmy.firmycrm.domain.FirmyCrmFacade;
import com.baza.firmy.firmycrm.dto.AktualizujSzczegolyKontaktuDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import com.baza.firmy.firmycrm.query.FirmaCrmViewEntity;
import com.baza.firmy.firmycrm.query.FirmyCrmFilterSpecification;
import com.baza.firmy.firmycrm.query.FirmyCrmQueryFacade;
import com.baza.firmy.firmysubscrypcje.dto.FirmaSubscrypcjaDto;
import com.baza.firmy.firmysubscrypcje.dto.ParametrySubscrypcjiDto;
import com.baza.firmy.firmysubscrypcje.query.FirmySubscrypcjeQueryFacade;
import com.baza.firmy.uzytkownicy.query.UzytkownicyQueryFacade;
import com.baza.firmy.uzytkownicy.query.UzytkownikViewEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/firmy")
@Tag(name = "FIRMY API", description = "Dostęp do firm")
class FirmyController {

    private final FirmyCrmQueryFacade firmyCrmQueryFacade;
    private final FirmyCrmFacade firmyCrmFacade;
    private final UzytkownicyQueryFacade uzytkownicyQueryFacade;
    private final FirmySubscrypcjeQueryFacade firmySubscrypcjeQueryFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Usługa pobierająca listę firm")
    public ResponseEntity<PageResponseDto<FirmaCrmListDto>> pobierzListeJdg(Pageable pageable,
                                                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = uzytkownicyQueryFacade.findByUuid(UUID.fromString(userPrincipal.userId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie istnieje"));

        FirmaSubscrypcjaDto firmaSubscrypcjaDto = firmySubscrypcjeQueryFacade.znajdzAktywnaSubscrypcjeDlaFirmy(uzytkownik.getFirma().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie posiada aktywnej subskrypcji"));

        Specification<FirmaCrmViewEntity> specification = createSpecification(firmaSubscrypcjaDto);
        Page<FirmaCrmListDto> page = firmyCrmQueryFacade.pobierzListeFirm(specification, pageable);
        return ResponseEntity.ok(PageResponseDto.from(page));
    }

    @PutMapping(value = "/kontakt", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Usługa aktualizująca szczegóły kontaktu z firmą")
    public ResponseEntity<Void> aktualizujSzczegolyKontaktu(
            @Valid @RequestBody AktualizujSzczegolyKontaktuDto dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = sprawdzUzytkownikaOrazSubscrypcje(userPrincipal);

        firmyCrmFacade.aktualizujSzczegolyKontaktu(uzytkownik.getFirma().getUuid(), dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{firmaCrmUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Usługa pobierająca szczegóły firmy CRM")
    public ResponseEntity<FirmaCrmDto> pobierzSzczegolyFirmy(
            @PathVariable UUID firmaCrmUuid,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = sprawdzUzytkownikaOrazSubscrypcje(userPrincipal);

        FirmaCrmDto firmaCrmDto = firmyCrmQueryFacade.pobierzSzczegolyFirmy(uzytkownik.getFirma().getUuid(), firmaCrmUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nie znaleziono firmy o UUID: " + firmaCrmUuid));

        return ResponseEntity.ok(firmaCrmDto);
    }

    private Specification<FirmaCrmViewEntity> createSpecification(FirmaSubscrypcjaDto firmaSubscrypcja) {
        SpecificationBuilder<FirmyCrmFilterSpecification> builder = SpecificationBuilder.specification(
                FirmyCrmFilterSpecification.class);
        ParametrySubscrypcjiDto parametry = firmaSubscrypcja.getParametrySubscrypcji();

        if (parametry != null) {
            parametry.getPkd().ifPresent(pkd -> builder.withParam("pkd", pkd));

            parametry.getDataRozpoczeciaOd().ifPresent(data ->
                    builder.withParam("dataRozpoczeciaOd", data.format(DateTimeFormatter.ISO_DATE)));

            parametry.getDataRozpoczeciaDo().ifPresent(data ->
                    builder.withParam("dataRozpoczeciaDo", data.format(DateTimeFormatter.ISO_DATE)));

            parametry.getWojewodztwo().ifPresent(woj -> builder.withParam("wojewodztwo", woj));

            parametry.getPowiat().ifPresent(pow -> builder.withParam("powiat", pow));

            parametry.getGmina().ifPresent(gm -> builder.withParam("gmina", gm));
        }

        builder.withParam("status", BusinessStatus.AKTYWNY.name());
        return builder.build();
    }

    private UzytkownikViewEntity sprawdzUzytkownikaOrazSubscrypcje(UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = uzytkownicyQueryFacade.findByUuid(UUID.fromString(userPrincipal.userId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie istnieje"));

        firmySubscrypcjeQueryFacade.znajdzAktywnaSubscrypcjeDlaFirmy(uzytkownik.getFirma().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie posiada aktywnej subskrypcji"));

        return uzytkownik;
    }

}
