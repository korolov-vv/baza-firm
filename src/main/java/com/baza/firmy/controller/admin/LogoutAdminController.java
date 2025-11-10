package com.baza.firmy.controller.admin;


import com.baza.firmy.common.service.user.AdminUserService;
import com.baza.firmy.request.LogoutRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/admin/logout")
@RequiredArgsConstructor
public class LogoutAdminController {

  private final AdminUserService adminUserService;

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logoutUser(@RequestBody @Valid LogoutRequest request) {
      adminUserService.logoutUser(request);
  }

}
