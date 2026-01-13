package com.baza.firmy.controller.admin;


import com.baza.firmy.firmysubscrypcje.domain.FirmySubscrypcjeFacade;
import com.baza.firmy.request.ZmienStatusSubscrypcjiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clienci/subscrypcje")
@Tag(name = "Firmy Subscrypcje API", description = "Dostęp Subscrypcji klientów")
public class FirmySubscrypcjeController {

    private final FirmySubscrypcjeFacade firmySubscrypcjeFacade;

    @PatchMapping("/zmien-status")
    @Operation(summary = "Zmień status subscrypcji firmy")
    public ResponseEntity<UUID> zmienStatusSubscrypcji(@Valid @RequestBody ZmienStatusSubscrypcjiRequest request) {
        UUID uuid = firmySubscrypcjeFacade.zaktualizujStatusSubscrypcjiFirmy(request);
        return ResponseEntity.ok(uuid);
    }
}
