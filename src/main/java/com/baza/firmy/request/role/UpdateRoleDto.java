package com.baza.firmy.request.role;

import lombok.Builder;

@Builder
public record UpdateRoleDto(UserRoleKeycloak userRoleKeycloak,
                            String userUuid) {
}
