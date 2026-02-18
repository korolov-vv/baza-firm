package com.baza.firmy.controller;

import com.baza.firmy.dto.KeycloakEventDto;
import com.baza.firmy.service.KeycloakEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/internal/keycloak")
@RequiredArgsConstructor
public class KeycloackWebhookController {

    private final KeycloakEventService keycloakEventService;

    @PostMapping("/events")
    public ResponseEntity<Void> handleKeycloakEvent(@RequestBody KeycloakEventDto event) {

        log.info("Received Keycloak event: type={}, userId={}", event.getType(), event.getUserId());

        try {
            keycloakEventService.processEvent(event);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error processing Keycloak event", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
