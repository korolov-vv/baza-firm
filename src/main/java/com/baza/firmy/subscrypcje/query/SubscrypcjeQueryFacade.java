package com.baza.firmy.subscrypcje.query;

import com.baza.firmy.subscrypcje.domain.dto.SubscrypcjaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscrypcjeQueryFacade {

  private final SubscrypcjeQueryRepository subscrypcjeQueryRepository;
  private final SubscrypcjeQueryMapper subscrypcjeQueryMapper;

  public SubscrypcjaDto findByUuid(UUID uuid) {
    return subscrypcjeQueryRepository.findByUuid(uuid)
            .map(subscrypcjeQueryMapper::toSubscrypcjaDto)
        .orElseThrow(() -> new RuntimeException("Nie znaleziono subscrypcji o UUID: " + uuid));
  }

  public Optional<SubscrypcjaDto> findByNazwa(String email) {
      return subscrypcjeQueryRepository.findByNazwa(email)
              .map(subscrypcjeQueryMapper::toSubscrypcjaDto);
  }
}
