package com.baza.firmy.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
class UpadloscDto {

  private String rodzajInformacji;
  private String dataOrzeczenia;
  private String imieSyndyka;
  private String nazwiskoSyndyka;
  private String nipSyndyka;
}
