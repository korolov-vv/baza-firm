package com.baza.firmy.podmiotygospodarcze.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GusSzczegolyDto {

  private int version;
  private UUID uuid;
  private String nazwa;
  private String nip;
}
