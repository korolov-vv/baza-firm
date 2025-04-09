package com.baza.firmy.service;

import com.baza.firmy.entity.ListaJdgPobieranie;
import com.baza.firmy.mapper.ListaJdgPobieranieMapper;
import com.baza.firmy.repository.ListaJdgPobieranieRepository;
import com.baza.firmy.response.ListaJdgDto;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListaJdgPobieranieService {

  private final ListaJdgPobieranieRepository listaJdgPobieranieRepository;
  private final ListaJdgPobieranieMapper listaJdgPobieranieMapper;

  Optional<ListaJdgPobieranie> znajdzOstatniaZapisanaListe(boolean czyStareDane) {
    return listaJdgPobieranieRepository.findFirstByCzyStareDaneOrderByIdDesc(czyStareDane);
  }

  Optional<ListaJdgPobieranie> znajdzOstatniaZapisanaListeDlaKamila() {
    return listaJdgPobieranieRepository.findFirstByCzyStareDaneOrderByIdDesc(false);
  }

  @Transactional
  UUID zapisz(ListaJdgDto listaJdgDto) {
    return listaJdgPobieranieRepository
        .save(listaJdgPobieranieMapper.toEntity(listaJdgDto))
        .getUuid();
  }

  @Transactional(TxType.REQUIRES_NEW)
  protected UUID zapisz(ListaJdgPobieranie listaJdgPobieranie) {
    return listaJdgPobieranieRepository.saveAndFlush(listaJdgPobieranie).getUuid();
  }

  ListaJdgPobieranie pobierzNieobsluzonaListeNowa() {
    return listaJdgPobieranieRepository.findFirstByCzyObsluzonaIsFalseAndCzyStareDaneIsFalse();
  }

  ListaJdgPobieranie pobierzNieobsluzonaListeStareDane() {
    return listaJdgPobieranieRepository.findFirstByCzyObsluzonaIsFalseAndCzyStareDaneIsTrueOrderByIdDesc();
  }
}
