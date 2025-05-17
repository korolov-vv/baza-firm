package com.baza.firmy.dto;

import java.util.List;
import java.util.Optional;

public interface JdgSzczegoly {

  Optional<String> getPkdGlowny();
  List<String> getPkd();
}
