package com.baza.firmy.podmiotygospodarcze.query;

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
public class PodmiotyGospodarczeQueryFacade {

  private final PodmiotyGospodarczeQueryRepository podmiotyGospodarczeQueryRepository;
  private final PodmiotyGospodarczeQueryMapper podmiotyGospodarczeQueryMapper;
  private final ExportujDaneDoXlsxUseCase exportujDaneDoXlsxUseCase;

  public Page<JdgListDto> pobierzListeJdg(Specification<PodmiotGospodarczeViewEntity> specification, Pageable pageable) {
    return podmiotyGospodarczeQueryRepository.findAll(specification, pageable)
        .map(podmiotyGospodarczeQueryMapper::toJdgListDtoList);
  }

  public List<String> pobierzLinkiDoJdgBezNipow() {
    return podmiotyGospodarczeQueryRepository.findAllByWlascicielNipIsNull().stream()
        .map(PodmiotGospodarczeViewEntity::getLink)
        .toList();
  }

  public boolean czyIstniejePoCeidgId(UUID ceidgId) {
    return podmiotyGospodarczeQueryRepository.existsByCeidgId(ceidgId);
  }

  public boolean existsByWlascicielNipAndDataRozpoczecia(
      String wlascicielNip, LocalDate dataRozpoczecia) {
    return podmiotyGospodarczeQueryRepository.existsByWlascicielNipAndDataRozpoczecia(
        wlascicielNip, dataRozpoczecia);
  }

  @Transactional
  public void exportujDoXlsx(ParametryWyszukiwaniaDto parametryWyszukiwaniaDto) {
    exportujDaneDoXlsxUseCase.exportujDoXlsx(parametryWyszukiwaniaDto);
  }

  public boolean czyIstniejePoKrs(String krs) {
    if (krs == null || krs.isBlank()) {
      return false;
    }
    final String krsDefault = "0000000000";
    final int krsSize = krs.length();
    final String krsNormalized = krsDefault.substring(0, (krsDefault.length() - krsSize)).concat(krs);
    return podmiotyGospodarczeQueryRepository.existsByNumerKrs(krsNormalized);
  }
}
