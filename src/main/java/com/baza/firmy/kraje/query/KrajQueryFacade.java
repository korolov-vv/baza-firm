package com.baza.firmy.kraje.query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KrajQueryFacade {

  private final KrajQueryRepository krajRepository;

  public List<KrajViewEntity> getKrajePoUuidList(List<UUID> uuidList) {
    List<KrajViewEntity> kraje = new ArrayList<>();
    uuidList.forEach(uuid ->
      krajRepository.findByUuid(uuid).ifPresent(kraje::add)
    );
    return kraje;
  }
}
