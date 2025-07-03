package com.baza.firmy.controller;

import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeFilterSpecification;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/jdg")
@Tag (name = "JDG API", description = "Dostęp do JDG")
class JdgController {

  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;

  @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation (summary = "Usługa pobierająca listę JDG")
  public ResponseEntity<Page<JdgListDto>> pobierzListeJdg(
      @Nullable @RequestParam String nazwa,
      @Nullable @RequestParam String pkd,
      @Nullable @RequestParam LocalDate dataRozpoczeciaOd,
      @Nullable @RequestParam LocalDate dataRozpoczeciaDo,
      @Nullable @RequestParam String status,
      @Nullable @RequestParam String wojewodztwo,
      @Nullable @RequestParam String powiat,
      @Nullable @RequestParam String gmina,
      Pageable pageable
  ) {
    Specification<PodmiotGospodarczeViewEntity> specification = SpecificationBuilder.specification(
            PodmiotyGospodarczeFilterSpecification.class)
        .withParam("nazwa", nazwa)
        .withParam("pkd", pkd != null ? pkd : "")
        .withParam("dataRozpoczeciaOd",
            dataRozpoczeciaOd != null ? dataRozpoczeciaOd.format(DateTimeFormatter.ISO_DATE) : null)
        .withParam("dataRozpoczeciaDo",
            dataRozpoczeciaDo != null ? dataRozpoczeciaDo.format(DateTimeFormatter.ISO_DATE) : null)
        .withParam("status", status)
        .withParam("wojewodztwo", wojewodztwo)
        .withParam("powiat", powiat)
        .withParam("gmina", gmina)
        .build();
    return ResponseEntity.ok(podmiotyGospodarczeQueryFacade.pobierzListeJdg(specification, pageable));
  }
}
