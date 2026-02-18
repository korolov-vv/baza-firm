package com.baza.firmy.podmiotygospodarcze.query;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import com.baza.firmy.dto.PodmiotGospodarczyListDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodmiotyGospodarczeQueryFacade {

  private final PodmiotyGospodarczeQueryRepository podmiotyGospodarczeQueryRepository;
  private final PodmiotyGospodarczeQueryMapper podmiotyGospodarczeQueryMapper;
  private final ExportujDaneDoXlsxUseCase exportujDaneDoXlsxUseCase;

  public Page<PodmiotGospodarczyListDto> pobierzListeJdg(Specification<PodmiotGospodarczyViewEntity> specification, Pageable pageable) {
    return pobierzListePodmiotowGospodarczych(specification, pageable)
        .map(podmiotyGospodarczeQueryMapper::toJdgListDtoList);
  }

  public Page<PodmiotGospodarczyViewEntity> pobierzListePodmiotowGospodarczych(Specification<PodmiotGospodarczyViewEntity> specification, Pageable pageable) {
    return podmiotyGospodarczeQueryRepository.findAll(specification, pageable);
  }

  public List<String> pobierzLinkiDoJdgBezNipow() {
    return podmiotyGospodarczeQueryRepository.findAllByWlascicielNipIsNull().stream()
        .map(PodmiotGospodarczyViewEntity::getLink)
        .toList();
  }

  public Optional<PodmiotGospodarczyViewEntity> pobierzAktywnaFirmePoNip(String nip) {
    List<PodmiotGospodarczyViewEntity> podmiotGospodarczeList = podmiotyGospodarczeQueryRepository.findAllByNipAndStatus(nip, BusinessStatus.AKTYWNY);

    if (podmiotGospodarczeList.isEmpty()) {
      return Optional.empty();
    }

    if (podmiotGospodarczeList.size() > 1) {
      log.info("Znaleziono więcej niż jedną aktywna firmę z NIP: {}", nip);
    }
    return Optional.of(podmiotGospodarczeList.getFirst());
  }

  public Optional<PodmiotGospodarczyViewEntity> pobierzPoUuid(UUID uuid) {
    return podmiotyGospodarczeQueryRepository.findByUuid(uuid);
  }

  public boolean czyIstniejePoCeidgId(UUID ceidgId) {
    return podmiotyGospodarczeQueryRepository.existsByCeidgId(ceidgId);
  }

  public boolean existsByNipAndDataRozpoczecia(
      String wlascicielNip, LocalDate dataRozpoczecia) {
    return podmiotyGospodarczeQueryRepository.existsByNipAndDataRozpoczecia(
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
