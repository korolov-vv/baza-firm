package com.baza.firmy.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequest(@NotBlank String refreshToken) {
}
