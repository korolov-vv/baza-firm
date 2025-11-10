package com.baza.firmy.controller;

import com.baza.firmy.common.exception.*;
import jakarta.ws.rs.WebApplicationException;
import org.apache.http.auth.InvalidCredentialsException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(value = EmailAlreadyInUseException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public ErrorMessage emailAlreadyInUseException(EmailAlreadyInUseException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = InvalidCredentialsException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage invalidCredentialsException(InvalidCredentialsException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = UserTemporaryDisabledException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage userTemporaryDisabled(UserTemporaryDisabledException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = InvalidTokenException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage invalidTokenException(InvalidTokenException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
  public ErrorMessage otpVerificationFailedException(UserNotFoundException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage runtimeException(RuntimeException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = WebApplicationException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage webApplicationException(WebApplicationException exception) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = IdentityManagementException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage identityManagementException(IdentityManagementException exception) {
    return new ErrorMessage(exception.getMessage());
  }
}
