package com.baza.firmy.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record KeycloakToken(@JsonProperty("session_state") String sessionId,
                            @JsonProperty("token_type") String tokenType,
                            @JsonProperty("access_token") String accessToken,
                            @JsonProperty("expires_in") String accessTokenExpiresInSeconds,
                            @JsonProperty("refresh_token") String refreshToken,
                            @JsonProperty("refresh_expires_in") String refreshTokenExpiresInSeconds) {
}