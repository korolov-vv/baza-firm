package com.baza.firmy.common.exception;

public class UserTemporaryDisabledException extends RuntimeException {

  public UserTemporaryDisabledException(String message, Throwable cause) {
    super(message, cause);
  }

}
