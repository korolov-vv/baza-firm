package com.baza.firmy.service;

import com.baza.firmy.entity.ListaJdgPobieranie;
import com.baza.firmy.mapper.ListaJdgPobieranieMapper;
import com.baza.firmy.repository.ListaJdgPobieranieRepository;
import com.baza.firmy.response.ListaJdgDto;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListaJdgPobieranieService {

  private final ListaJdgPobieranieRepository listaJdgPobieranieRepository;
  private final ListaJdgPobieranieMapper listaJdgPobieranieMapper;

  Optional<ListaJdgPobieranie> znajdzOstatniaZapisanaListe() {
    return listaJdgPobieranieRepository.findFirstByCzyStareDaneOrderByIdDesc(true);
  }

  UUID zapisz(ListaJdgDto listaJdgDto) {
    return listaJdgPobieranieRepository
        .save(listaJdgPobieranieMapper.toEntity(listaJdgDto))
        .getUuid();
  }
}
