package com.baza.firmy.common.exception;

public class IdentityManagementException extends RuntimeException {

  public IdentityManagementException(String message) {
    super(message);
  }

  public IdentityManagementException(String message, Throwable cause) {
    super(message, cause);
  }

}
