package com.baza.firmy.controller.customer;


import com.baza.firmy.common.service.user.CustomerUserService;
import com.baza.firmy.request.LogoutRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/cust/logout")
@RequiredArgsConstructor
public class LogoutCustomerController {

  private final CustomerUserService customerUserService;

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logoutUser(@RequestBody @Valid LogoutRequest request) {
      customerUserService.logoutUser(request);
  }

}
