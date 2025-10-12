package com.baza.firmy.common.service.user;

import com.baza.firmy.common.client.KeycloakAdminBazafirmClient;
import com.baza.firmy.common.client.KeycloakAuthAdminClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminUserService extends UserService {

  public AdminUserService(KeycloakAdminBazafirmClient keycloakAdminClient,
                          KeycloakAuthAdminClient keycloakAuthClient) {
    super(keycloakAdminClient, keycloakAuthClient);
  }
}
