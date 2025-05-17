package com.baza.firmy.pkd.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PkdQueryFasade {

  private final PkdQueryRepository pkdQueryRepository;

  public Optional<PkdViewEntity> findByKod(String kod) {
    return pkdQueryRepository.findByKod(kod);
  }

  public PkdViewEntity findByUuid(UUID uuid) {
    return pkdQueryRepository.findByUuid(uuid)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono PKD o podanym UUID: " + uuid));
  }

  public List<PkdViewEntity> findByUuidList(List<UUID> uuid) {
    List<PkdViewEntity> pkdViewEntities = new ArrayList<>();
    uuid.forEach(uuidItem ->
      pkdQueryRepository.findByUuid(uuidItem).ifPresent(pkdViewEntities::add)
    );
    return pkdViewEntities;
  }
}
