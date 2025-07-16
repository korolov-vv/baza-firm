package com.baza.firmy.podmiotygospodarcze.query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PodmiotyGospodarczeQueryRepository extends JpaRepository<PodmiotGospodarczeViewEntity, Long>, JpaSpecificationExecutor<PodmiotGospodarczeViewEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  boolean existsByNipAndDataRozpoczecia(String nip, LocalDate dataRozpoczecia);

  List<PodmiotGospodarczeViewEntity> findAllByWlascicielNipIsNull();

  boolean existsByNumerKrs(String krs);
}
