package com.baza.firmy.common.client;

import com.baza.firmy.common.exception.EmailAlreadyInUseException;
import com.baza.firmy.common.exception.IdentityManagementException;
import com.baza.firmy.configuration.properties.KeycloakProperties;
import com.baza.firmy.constants.KeycloakConstants;
import com.baza.firmy.constants.UzytkownikConstants;
import com.baza.firmy.request.SignupRequest;
import com.baza.firmy.request.role.UpdateRoleDto;
import com.baza.firmy.response.KeycloakAttackDetection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

import static com.baza.firmy.constants.KeycloakConstants.*;

@Slf4j
@RequiredArgsConstructor
public abstract class KeycloakAdminClient {

  private final ObjectMapper objectMapper;
  private final Keycloak keycloak;
  private final KeycloakProperties keycloakProperties;

  public String createUser(SignupRequest signupRequest) {
    final var userRepresentation = toUserRepresentation(signupRequest);
    final var realmResource = keycloak.realm(keycloakProperties.getRealm());
    setGroup(userRepresentation, realmResource);
    final var response = realmResource.users().create(userRepresentation);
    if (response.getStatus() == HttpStatus.SC_CONFLICT) {
      throw new EmailAlreadyInUseException(UzytkownikConstants.USER_ALREADY_EXISTS_EMAIL + signupRequest.email());
    }
    return Optional.of(response)
        .filter(res -> res.getStatus() == HttpStatus.SC_CREATED)
        .map(res -> keycloakId(res.getLocation().getPath()))
        .orElseThrow(() -> new ClientErrorException(response));
  }

  public void addRole(UpdateRoleDto updateRoleDto) {
    try {
      log.info("Add Role for User: UUID={}", updateRoleDto.userUuid());
      final var realmResource = keycloak.realm(keycloakProperties.getRealm());
      final var keycloakUser = realmResource.users().get(updateRoleDto.userUuid());
      if (keycloakUser == null) {
        log.warn(CANNOT_FIND_KC_USER_UUID, updateRoleDto.userUuid());
        throw new IdentityManagementException(CANNOT_FIND_USER);
      }

      setRole(updateRoleDto.userRoleKeycloak().getValue(), realmResource, updateRoleDto.userUuid());

    } catch (WebApplicationException exception) {
      log.error(CANNOT_UPDATE_KC_USER, exception.getResponse(), exception);
      throw new IdentityManagementException(CANNOT_UPDATE_USER, exception);
    }
  }

  public Optional<UserRepresentation> getUserByEmail(String email) {
    try {
      log.info("Get keycloak user: email={}", email);
      final var resource = keycloak.realm(keycloakProperties.getRealm());
      return resource.users().searchByEmail(email, true).stream()
          .filter(representation -> representation.getEmail().equals(email))
          .findFirst();
    } catch (WebApplicationException exception) {
      log.error("Can't get keycloak user. Cause: {}", exception.getResponse(), exception);
      throw new IdentityManagementException("Cannot find user", exception);
    }
  }

  public void deleteUser(String userUuid) {
    try {
      log.info("Delete keycloak user: UUID={}", userUuid);
      final var resource = keycloak.realm(keycloakProperties.getRealm());
      final var keycloakUsers = resource.users();
      Optional<UserRepresentation> userRepresentation =
          Optional.ofNullable(keycloakUsers.get(userUuid).toRepresentation());
      userRepresentation.ifPresentOrElse(
          representation -> keycloakUsers.delete(representation.getId()),
          () -> {
            throw new IdentityManagementException("Cannot find Keycloak user with UUID: " + userUuid);
          });
    } catch (WebApplicationException exception) {
      log.error(KeycloakConstants.CANNOT_DELETE_KC_USER_LOG, exception.getResponse(), exception);
      throw new IdentityManagementException(
          String.format(KeycloakConstants.CANNOT_FIND_KC_USER_ID, userUuid), exception);
    }
  }

  public boolean detectAttack(String userId) {
    final var status = keycloak.realms().realm(keycloakProperties.getRealm())
        .attackDetection().bruteForceUserStatus(userId);
    final var keycloakAttackDetection = objectMapper.convertValue(status,
        KeycloakAttackDetection.class);
    return keycloakAttackDetection.userDisabled();
  }

  public void resetUserPassword(String userId, String password) {
    final var credentials = new CredentialRepresentation();
    credentials.setTemporary(false);
    credentials.setValue(password);
    keycloak.realm(keycloakProperties.getRealm()).users().get(userId).resetPassword(credentials);
  }

  protected GroupRepresentation findGroupRepresentation(
      RealmResource realmResource, String groupName) {
    return realmResource.groups().groups().stream()
        .filter(group -> group.getName()
            .equals(groupName))
        .findFirst()
        .orElseThrow(() ->
            new IdentityManagementException(String.format(CANNOT_FIND_GROUP, groupName)));
  }

  /**
   * Extract userId: /admin/realms/{realm}/users/{userId} --> {userId}.
   */
  private String keycloakId(String locationUrl) {
    var parts = locationUrl.split("/");
    return parts[parts.length - 1];
  }

  private UserRepresentation toUserRepresentation(SignupRequest request) {
    final var userRepresentation = new UserRepresentation();
    userRepresentation.setEmail(request.email());
    userRepresentation.setCredentials(List.of(toCredentialRepresentation(request.password())));
    userRepresentation.setEnabled(true);
    return userRepresentation;
  }

  private CredentialRepresentation toCredentialRepresentation(String password) {
    final var credentialRepresentation = new CredentialRepresentation();
    credentialRepresentation.setTemporary(false);
    credentialRepresentation.setValue(password);
    return credentialRepresentation;
  }

  private void setRole(String userTypeValue, RealmResource realmResource,
      String userId) {
    final var role = realmResource.roles().get(userTypeValue).toRepresentation();
    realmResource.users()
        .get(userId)
        .roles()
        .realmLevel()
        .add(List.of(role));
  }

  protected abstract void setGroup(UserRepresentation userRepresentation,
      RealmResource realmResource);
}
