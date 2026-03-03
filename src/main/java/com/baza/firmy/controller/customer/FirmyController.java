package com.baza.firmy.controller.customer;

import com.baza.firmy.dto.PageResponseDto;
import com.baza.firmy.dto.UserPrincipal;
import com.baza.firmy.firmycrm.domain.FirmyCrmFacade;
import com.baza.firmy.firmycrm.dto.AktualizujSzczegolyKontaktuDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmFiltryDto;
import com.baza.firmy.firmycrm.dto.FirmaCrmListDto;
import com.baza.firmy.firmycrm.query.FirmyCrmQueryFacade;
import com.baza.firmy.firmysubscrypcje.query.FirmySubscrypcjeQueryFacade;
import com.baza.firmy.uzytkownicy.query.UzytkownicyQueryFacade;
import com.baza.firmy.uzytkownicy.query.UzytkownikViewEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<PageResponseDto<FirmaCrmListDto>> pobierzListeFirm(
            Pageable pageable,
            FirmaCrmFiltryDto filtry,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = sprawdzUzytkownikaOrazSubscrypcje(userPrincipal);

        filtry.setUuidFirmyKlienta(uzytkownik.getFirma().getUuid());
        Page<FirmaCrmListDto> page = firmyCrmQueryFacade.pobierzListeFirm(filtry, pageable);
        return ResponseEntity.ok(PageResponseDto.from(page));
    }

    @GetMapping(value = "/do-kontaktu-dzis", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Usługa pobierająca listę firm")
    public ResponseEntity<PageResponseDto<FirmaCrmListDto>> pobierzListeFirmDoKontaktuDzis(
            Pageable pageable,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = sprawdzUzytkownikaOrazSubscrypcje(userPrincipal);

        FirmaCrmFiltryDto filtry = FirmaCrmFiltryDto.builder()
                .uuidFirmyKlienta(uzytkownik.getFirma().getUuid())
                .build();

        Page<FirmaCrmListDto> page = firmyCrmQueryFacade.pobierzListeFirmDoKontaktuDzis(filtry, pageable);
        return ResponseEntity.ok(PageResponseDto.from(page));
    }

    @GetMapping(value = "/do-kontaktu-dzis/czy-sa", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Usługa pobierająca listę firm")
    public ResponseEntity<Boolean> czySaFirmyDoKontaktuDzis(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = sprawdzUzytkownikaOrazSubscrypcje(userPrincipal);

        FirmaCrmFiltryDto filtry = FirmaCrmFiltryDto.builder()
                .uuidFirmyKlienta(uzytkownik.getFirma().getUuid())
                .build();

        return ResponseEntity.ok(firmyCrmQueryFacade.czySaFirmyDoKontaktuDzis(filtry));
    }


    @PutMapping(value = "/kontakt", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Usługa aktualizująca szczegóły kontaktu z firmą")
    public ResponseEntity<FirmaCrmDto> aktualizujSzczegolyKontaktu(
            @Valid @RequestBody AktualizujSzczegolyKontaktuDto dto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = sprawdzUzytkownikaOrazSubscrypcje(userPrincipal);

        FirmaCrmDto zaktualizowanaFirma = firmyCrmFacade.aktualizujSzczegolyKontaktu(uzytkownik.getFirma().getUuid(), dto);
        return ResponseEntity.ok(zaktualizowanaFirma);
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

    private UzytkownikViewEntity sprawdzUzytkownikaOrazSubscrypcje(UserPrincipal userPrincipal) {
        UzytkownikViewEntity uzytkownik = uzytkownicyQueryFacade.findByUuid(UUID.fromString(userPrincipal.userId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie istnieje"));

        firmySubscrypcjeQueryFacade.znajdzAktywnaSubscrypcjeDlaFirmy(uzytkownik.getFirma().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie posiada aktywnej subskrypcji"));

        return uzytkownik;
    }

}
