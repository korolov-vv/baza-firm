package com.baza.firmy.common.service.user;

import com.baza.firmy.common.client.KeycloakAdminClient;
import com.baza.firmy.common.client.KeycloakAuthClient;
import com.baza.firmy.common.exception.IdentityManagementException;
import com.baza.firmy.common.exception.InvalidCredentialsException;
import com.baza.firmy.common.exception.UserNotFoundException;
import com.baza.firmy.common.exception.UserTemporaryDisabledException;
import com.baza.firmy.constants.KeycloakConstants;
import com.baza.firmy.constants.UzytkownikConstants;
import com.baza.firmy.request.*;
import com.baza.firmy.response.KeycloakToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final KeycloakAdminClient keycloakAdminClient;
  private final KeycloakAuthClient keycloakAuthClient;

  public KeycloakToken createUser(SignupRequest signupRequest) {
    keycloakAdminClient.createUser(signupRequest);

    return generateToken(signupRequest);
  }

  public KeycloakToken generateToken(LoginRequest loginRequest) {
    try {
      log.info("Service: Login request received for email: {} and password: {}",
          loginRequest.email(), loginRequest.password());
      return keycloakAuthClient.generateToken(loginRequest);
    } catch (WebClientResponseException exception) {
      if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
        handleAuthorizationFailure(loginRequest.email(), exception);
      }
      log.error(KeycloakConstants.AUTHORIZATION_CALL_FAILED, exception);
      throw new IdentityManagementException(KeycloakConstants.AUTHORIZATION_CALL_FAILED, exception);
    }
  }

  private KeycloakToken generateToken(SignupRequest signupRequest) {
    final var loginRequest = LoginRequest.builder()
        .email(signupRequest.email())
        .password(signupRequest.password())
        .build();
    return generateToken(loginRequest);
  }

  public KeycloakToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
    return keycloakAuthClient.refreshToken(refreshTokenRequest);
  }

  public void logoutUser(LogoutRequest request) {
    keycloakAuthClient.logoutUser(request);
  }

  public void resetUserPassword(ResetPasswordRequest resetPasswordRequest) {
    final var email = resetPasswordRequest.email();
    final var user = keycloakAdminClient.getUserByEmail(email)
        .orElseThrow(() -> new UserNotFoundException(UzytkownikConstants.NO_USER_WITH_EMAIL + email));
    keycloakAdminClient.resetUserPassword(user.getId(), resetPasswordRequest.password());
  }

  private void handleAuthorizationFailure(String email, WebClientResponseException exception) {
    log.warn(UzytkownikConstants.FAILD_TO_AUTHORIZE, exception);
    keycloakAdminClient.getUserByEmail(email)
        .filter(userRepresentation -> keycloakAdminClient.detectAttack(userRepresentation.getId()))
        .ifPresentOrElse(
            attack -> {
              throw new UserTemporaryDisabledException(UzytkownikConstants.USER_TEMP_DISABLED, exception);
            },
            () -> {
                throw new InvalidCredentialsException(UzytkownikConstants.INVALID_CREDENTIALS, exception);
            });
  }

}
