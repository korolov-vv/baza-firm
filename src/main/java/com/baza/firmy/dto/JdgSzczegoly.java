package com.baza.firmy.dto;

import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;

import java.util.List;
import java.util.Optional;

public interface JdgSzczegoly {

  Optional<Pkd> getPkdGlowny();
  List<Pkd> getPkd();
}
