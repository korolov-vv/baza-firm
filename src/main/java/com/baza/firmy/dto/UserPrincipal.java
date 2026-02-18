package com.baza.firmy.dto;

import java.util.List;

public record UserPrincipal(String userId, List<String> roles) {

}
