package com.baza.firmy.jdg.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
class OgraniczeniaZdolnosciPrawnejDto {

  private String ustanowienieKuratora;
  private String trybRestrykcji;
  private String dataRestrykcji;
  private String wprowadzonePrzez;
  private String imieKuratora;
  private String nazwiskoKuratora;
  private String nipKuratora;
}
