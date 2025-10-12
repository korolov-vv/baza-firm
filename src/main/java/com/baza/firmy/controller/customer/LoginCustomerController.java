package com.baza.firmy.controller.customer;

import com.baza.firmy.common.service.user.CustomerUserService;
import com.baza.firmy.request.LoginRequest;
import com.baza.firmy.request.RefreshTokenRequest;
import com.baza.firmy.request.ResetPasswordRequest;
import com.baza.firmy.request.SignupRequest;
import com.baza.firmy.response.KeycloakToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cust/login")
@RequiredArgsConstructor
public class LoginCustomerController {

  private final CustomerUserService customerUserService;

  @PostMapping
  public KeycloakToken generateToken(@RequestBody @Valid LoginRequest loginRequest) {
    return customerUserService.generateToken(loginRequest);
  }

  @PostMapping("/signup")
  public KeycloakToken registerNewUser(@RequestBody @Valid SignupRequest signupRequest) {
    return customerUserService.createUser(signupRequest);
  }

  @PostMapping("/refresh")
  public KeycloakToken refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    return customerUserService.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/password-reset")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void resetUserPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
      customerUserService.resetUserPassword(resetPasswordRequest);
  }
}
