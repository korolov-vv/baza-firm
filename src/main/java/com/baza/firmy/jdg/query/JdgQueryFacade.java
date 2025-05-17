package com.baza.firmy.jdg.query;

import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JdgQueryFacade {

  private final JdgQueryRepository jdgQueryRepository;
  private final JdgQueryMapper jdgQueryMapper;
  private final ExportujDaneDoXlsxUseCase exportujDaneDoXlsxUseCase;

  public Page<JdgListDto> pobierzListeJdg(Specification<JdgViewEntity> specification, Pageable pageable) {
    return jdgQueryRepository.findAll(specification, pageable)
        .map(jdgQueryMapper::toJdgListDtoList);
  }

  public List<String> pobierzLinkiDoJdgBezNipow() {
    return jdgQueryRepository.findAllByWlascicielNipIsNull().stream()
        .map(JdgViewEntity::getLink)
        .toList();
  }

  public boolean czyIstniejePoCeidgId(UUID ceidgId) {
    return jdgQueryRepository.existsByCeidgId(ceidgId);
  }

  public boolean existsByWlascicielNipAndNazwaAndDataRozpoczecia(
      String wlascicielNip, String nazwa, LocalDate dataRozpoczecia) {
    return jdgQueryRepository.existsByWlascicielNipAndNazwaAndDataRozpoczecia(
        wlascicielNip, nazwa, dataRozpoczecia);
  }

  @Transactional
  public void exportujDoXlsx(ParametryWyszukiwaniaDto parametryWyszukiwaniaDto) {
    exportujDaneDoXlsxUseCase.exportujDoXlsx(parametryWyszukiwaniaDto);
  }
}
