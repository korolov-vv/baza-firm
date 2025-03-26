package com.baza.firmy.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ParametryWyszukiwaniaDto {
  private String nazwa;
  private String pkd;
  private LocalDate dataRozpoczecia;
  private LocalDateTime createDate;
  private String status;
  private String wojewodztwo;
  private String powiat;
  private String gmina;
}
