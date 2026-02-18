package com.baza.firmy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeycloakEventDto {

    private String type;

    @JsonProperty("realmId")
    private String realmId;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("ipAddress")
    private String ipAddress;

    private Long time;

    private Map<String, String> details;

    private String error;

    public Instant getEventTime() {
        return time != null ? Instant.ofEpochMilli(time) : null;
    }
}

