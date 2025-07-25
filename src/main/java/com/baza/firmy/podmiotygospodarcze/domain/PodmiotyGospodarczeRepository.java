package com.baza.firmy.podmiotygospodarcze.domain;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PodmiotyGospodarczeRepository extends JpaRepository<PodmiotGospodarczyEntity, Long>, JpaSpecificationExecutor<PodmiotGospodarczyEntity> {

  Optional<PodmiotGospodarczyEntity> findByNipAndDataRozpoczecia(String nip, LocalDate dataRozpoczecia);
  Optional<PodmiotGospodarczyEntity> findFirstByNipOrderByDataRozpoczeciaDesc(String nip);

  Optional<PodmiotGospodarczyEntity> findByNumerKrs(String krs);
}
