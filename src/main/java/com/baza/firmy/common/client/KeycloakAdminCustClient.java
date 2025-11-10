package com.baza.firmy.common.client;

import com.baza.firmy.configuration.properties.KeycloakBazafirmCustomerProperties;
import com.baza.firmy.request.UserGroupsKeycloak;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Keycloak klient dla użytkowników portalu.
 */
@Slf4j
@Component
public class KeycloakAdminCustClient extends KeycloakAdminClient {

  public KeycloakAdminCustClient(ObjectMapper objectMapper,
                                 @Qualifier("keycloakBazafirmCustomer") Keycloak keycloak,
                                 KeycloakBazafirmCustomerProperties keycloakProperties) {
    super(objectMapper, keycloak, keycloakProperties);
  }

  @Override
  protected void setGroup(UserRepresentation userRepresentation,
      RealmResource realmResource) {
    userRepresentation.setGroups(
        List.of(findGroupRepresentation(
            realmResource, UserGroupsKeycloak.CUSTOMER_USERS_GROUP.getValue()).getName()));
  }
}
