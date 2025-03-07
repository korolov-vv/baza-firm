package com.baza.firmy.controller;

import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.repository.JdgFilterSpecification;
import com.baza.firmy.service.JdgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jdg")
@Tag(name = "JDG API", description = "Dostęp do JDG")
class JdgController {

 private final JdgService jdgService;

 @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
 @Operation (summary = "Usługa pobierająca listę JDG")
 public ResponseEntity<List<JdgListDto>> pobierzListeJdg(JdgFilterSpecification specification) {
  return ResponseEntity.ok(jdgService.pobierzListeJdg(specification));
 }
}
