package com.baza.firmy.request;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ZmienStatusSubscrypcjiRequest(
    @NotNull UUID uuidSubscrypcji,
    @NotNull StatusSubscrypcji nowyStatus
) {
}

