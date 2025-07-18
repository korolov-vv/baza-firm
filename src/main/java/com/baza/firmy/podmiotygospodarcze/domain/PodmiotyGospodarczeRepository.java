package com.baza.firmy.podmiotygospodarcze.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PodmiotyGospodarczeRepository extends JpaRepository<PodmiotGospodarczeEntity, Long>, JpaSpecificationExecutor<PodmiotGospodarczeEntity> {

  Optional<PodmiotGospodarczeEntity> findByNipAndDataRozpoczecia(String nip, LocalDate dataRozpoczecia);
  List<PodmiotGospodarczeEntity> findAllByNipAndDataRozpoczecia(String nip, LocalDate dataRozpoczecia);

  Optional<PodmiotGospodarczeEntity> findByNumerKrs(String krs);
}
