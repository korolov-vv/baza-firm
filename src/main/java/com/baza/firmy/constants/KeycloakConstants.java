package com.baza.firmy.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeycloakConstants {

  public static final String KEYCLOAK_JWT_USER_ID = "sub";
  public static final String AUTHORIZATION_CALL_FAILED = "Authorization call to Keycloak failed";
  public static final String CANNOT_UPDATE_KC_USER = "Can't update keycloak user. Cause: {}";
  public static final String CANNOT_DELETE_KC_USER_LOG = "Can't delete keycloak user. Cause: {}";
  public static final String CANNOT_FIND_KC_USER_UUID = "Can't find keycloak user with UUID: {}";
  public static final String CANNOT_FIND_KC_USER_ID = "Cannot find Keycloak user with UUID: %s";
  public static final String CANNOT_UPDATE_USER = "Cannot update user";
  public static final String CANNOT_FIND_USER = "Cannot find user";
  public static final String BUSINESS_UUID = "businessUuid";
  public static final String CANNOT_FIND_GROUP = "Cannot find user's group with name: %s";
}
