package com.baza.firmy.common.service.user;

import com.baza.firmy.common.client.KeycloakAdminCustClient;
import com.baza.firmy.common.client.KeycloakAuthCustomerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerUserService extends UserService {

  public CustomerUserService(KeycloakAdminCustClient keycloakAdminClient,
                             KeycloakAuthCustomerClient keycloakAuthClient) {
    super(keycloakAdminClient, keycloakAuthClient);
  }
}
