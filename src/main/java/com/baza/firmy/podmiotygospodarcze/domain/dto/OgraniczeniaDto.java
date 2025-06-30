package com.baza.firmy.podmiotygospodarcze.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
class OgraniczeniaDto {

  private String dataOd;
  private String dataDo;
  private String nazwa;
  private String opis;
  private String wprowadzonePrzez;
}
