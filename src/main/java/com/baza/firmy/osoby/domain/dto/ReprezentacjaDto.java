package com.baza.firmy.osoby.domain.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ReprezentacjaDto {

  private String nazwaOrganu;
  private String sposobReprezentacji;
  @Builder.Default
  private List<ReprezentantDto> sklad = new ArrayList<>();
}
