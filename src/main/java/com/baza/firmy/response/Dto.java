package com.baza.firmy.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
public class Dto {

  @Builder.Default
  private List<JdgSzczegolyDto> firma = new ArrayList<>();
}
