package com.baza.firmy.pkd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
public class PkdDto {

  private UUID uuid;
  private String kod;
  private String nazwa;
}
