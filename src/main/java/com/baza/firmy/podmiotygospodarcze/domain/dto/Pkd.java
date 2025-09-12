package com.baza.firmy.podmiotygospodarcze.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
public class Pkd {

  private String kod;
  private String nazwa;
}
