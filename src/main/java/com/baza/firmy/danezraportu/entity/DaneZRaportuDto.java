package com.baza.firmy.danezraportu.entity;

import com.baza.firmy.dto.JdgSzczegolyRaportDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DaneZRaportuDto {

  private int version;
  private String wojewodztwo;
  @Builder.Default
  private List<JdgSzczegolyRaportDto> dzialalnosci = new ArrayList<>();
}
