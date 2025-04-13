package com.baza.firmy.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParametryWyszukiwaniaDto {
  private String nazwa;
  private String pkd;
  private LocalDate dataRozpoczeciaOd;
  private LocalDate dataRozpoczeciaDo;
  private LocalDateTime createDate;
  private String status;
  private String wojewodztwo;
  private String powiat;
  private String gmina;
}
