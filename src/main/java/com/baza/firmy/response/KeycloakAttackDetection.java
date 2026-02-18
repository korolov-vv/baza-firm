package com.baza.firmy.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakAttackDetection(@JsonProperty("lastFailure") long lastFailureMs,
                                      @JsonProperty("disabled") boolean userDisabled,
                                      @JsonProperty("numFailures") int numberOfFailures) {
}
