package com.baza.firmy.controller.customer;

import com.baza.firmy.dto.UserPrincipal;
import com.baza.firmy.uzytkownicy.dto.UzytkownikDto;
import com.baza.firmy.uzytkownicy.query.UzytkownicyQueryFacade;
import com.baza.firmy.uzytkownicy.query.UzytkownicyQueryMapper;
import com.baza.firmy.uzytkownicy.query.UzytkownikViewEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/uzytkownicy")
@Tag (name = "FIRMY API", description = "Dostęp do danych użytkownika")
class UzytkownicyController {

  private final UzytkownicyQueryFacade uzytkownicyQueryFacade;
  private final UzytkownicyQueryMapper uzytkownicyQueryMapper;

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Usługa pobierająca dane zalogowanego użytkownika")
  public ResponseEntity<UzytkownikDto> pobierzDaneUzytkownika(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    UzytkownikViewEntity uzytkownik = uzytkownicyQueryFacade.findByUuid(UUID.fromString(userPrincipal.userId()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie istnieje"));

    UzytkownikDto uzytkownikDto = uzytkownicyQueryMapper.toUzytkownikDto(uzytkownik);
    return ResponseEntity.ok(uzytkownikDto);
  }
}
