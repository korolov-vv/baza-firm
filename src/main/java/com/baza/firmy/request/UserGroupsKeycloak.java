package com.baza.firmy.request;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserGroupsKeycloak {
  ADMIN_USERS_GROUP("admin-users-group"),
  CUSTOMER_USERS_GROUP("customer-users-group");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

}
