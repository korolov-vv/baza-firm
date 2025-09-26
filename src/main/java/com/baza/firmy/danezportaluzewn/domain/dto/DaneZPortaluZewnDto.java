package com.baza.firmy.danezportaluzewn.domain.dto;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DaneZPortaluZewnDto {

  private UUID uuid;
  private List<FirmaPortalZewnDto> firmy = new ArrayList<>();
  private String pageLink;
  private String nextPageLink;
  private StatusPobieraniaEnum statusPobierania;
  @Builder.Default
  private List<FirmaPortalZewnDto> niepobraneFirmy = new ArrayList<>();
  private String blad;
}
