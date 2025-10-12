package com.baza.firmy.controller.admin;

import com.baza.firmy.common.service.user.AdminUserService;
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
@RequestMapping("/api/v1/admin/login")
@RequiredArgsConstructor
public class LoginAdminController {

  private final AdminUserService adminUserService;

  @PostMapping
  public KeycloakToken generateToken(@RequestBody @Valid LoginRequest loginRequest) {
    return adminUserService.generateToken(loginRequest);
  }

  @PostMapping("/signup")
  public KeycloakToken registerNewUser(@RequestBody @Valid SignupRequest signupRequest) {
    return adminUserService.createUser(signupRequest);
  }

  @PostMapping("/refresh")
  public KeycloakToken refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    return adminUserService.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/password-reset")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void resetUserPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
    adminUserService.resetUserPassword(resetPasswordRequest);
  }
}
