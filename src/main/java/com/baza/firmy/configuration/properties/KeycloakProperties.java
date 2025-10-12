package com.baza.firmy.configuration.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KeycloakProperties {

  private String serverUrl;
  private String realm;
  private String authUrl;
  private String introspectTokenUrl;
  private String logoutUrl;
  private String adminClientId;
  private String adminClientSecret;
  private List<KeycloakClient> clients;

  @Getter
  @Setter
  public static class KeycloakClient {

    private String clientName;
    private String clientId;
    private String clientSecret;

  }


}
