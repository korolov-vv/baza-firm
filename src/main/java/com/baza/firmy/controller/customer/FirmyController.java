package com.baza.firmy.controller.customer;

import com.baza.firmy.dto.PodmiotGospodarczyListDto;
import com.baza.firmy.dto.UserPrincipal;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeFilterSpecification;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import com.baza.firmy.subscrypcje.query.SubscrypcjeQueryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/firmy")
@Tag (name = "FIRMY API", description = "Dostęp do firm")
class FirmyController {

  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
  private final SubscrypcjeQueryFacade subscrypcjeQueryFacade;

  @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation (summary = "Usługa pobierająca listę firm")
  public ResponseEntity<Page<PodmiotGospodarczyListDto>> pobierzListeJdg(
          @Nullable @RequestParam String nazwa,
          @Nullable @RequestParam String pkd,
          @Nullable @RequestParam LocalDate dataRozpoczeciaOd,
          @Nullable @RequestParam LocalDate dataRozpoczeciaDo,
          @Nullable @RequestParam String wojewodztwo,
          @Nullable @RequestParam String powiat,
          @Nullable @RequestParam String gmina,
          Pageable pageable,
          @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    subscrypcjeQueryFacade.znajdzAktywnaSubscrypcjeUzytkownika(UUID.fromString(userPrincipal.userId()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Użytkownik nie posiada aktywnej subskrypcji"));

    Specification<PodmiotGospodarczeViewEntity> specification = SpecificationBuilder.specification(
            PodmiotyGospodarczeFilterSpecification.class)
        .withParam("nazwa", nazwa)
        .withParam("pkd", pkd != null ? pkd : "")
        .withParam("dataRozpoczeciaOd",
            dataRozpoczeciaOd != null ? dataRozpoczeciaOd.format(DateTimeFormatter.ISO_DATE) : null)
        .withParam("dataRozpoczeciaDo",
            dataRozpoczeciaDo != null ? dataRozpoczeciaDo.format(DateTimeFormatter.ISO_DATE) : null)
        .withParam("wojewodztwo", wojewodztwo)
        .withParam("powiat", powiat)
        .withParam("gmina", gmina)
        .build();
    return ResponseEntity.ok(podmiotyGospodarczeQueryFacade.pobierzListeJdg(specification, pageable));
  }
}
