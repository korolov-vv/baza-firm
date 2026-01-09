package com.baza.firmy.uzytkownicy.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class StworzUzytkownikaDto {

  private UUID keycloakUuid;
  @NonNull
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
  private String email;
  @NonNull
  @Pattern(regexp = "^[0-9]{11}$")
  private String nip;
}
