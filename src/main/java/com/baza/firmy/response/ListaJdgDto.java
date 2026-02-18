package com.baza.firmy.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ListaJdgDto {

  private List<CeidgListDto> firmy;
  private Long count;
  private Links links;

  private boolean czyStareDane;
}
