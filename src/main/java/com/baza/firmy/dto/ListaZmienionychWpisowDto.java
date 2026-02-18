package com.baza.firmy.dto;

import com.baza.firmy.response.Links;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ListaZmienionychWpisowDto {

  private UUID uuid;
  private List<String> identyfikatoryWpisow;
  private Long count;
  private Links links;

  private boolean czyStareDane;
}
