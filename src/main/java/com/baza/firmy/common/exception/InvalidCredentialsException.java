package com.baza.firmy.common.exception;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException(String message, Throwable cause) {
    super(message, cause);
  }

}
