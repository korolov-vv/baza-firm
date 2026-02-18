package com.baza.firmy.request.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum UserRoleKeycloak {
  CUSTOMER("customer"),
  ADMIN("admin");

  private final String value;

  private static final Map<String, UserRoleKeycloak> USER_ROLE_MAP = Arrays.stream(UserRoleKeycloak.values())
      .collect(Collectors.toMap(UserRoleKeycloak::getValue, Function.identity()));

  @JsonCreator
  public static UserRoleKeycloak ofValue(String value) {
    if (StringUtils.isBlank(value)) {
      return null;
    }
    return USER_ROLE_MAP.get(value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}
