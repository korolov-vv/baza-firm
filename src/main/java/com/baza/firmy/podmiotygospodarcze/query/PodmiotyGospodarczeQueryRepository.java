package com.baza.firmy.podmiotygospodarcze.query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PodmiotyGospodarczeQueryRepository extends JpaRepository<PodmiotyGospodarczeViewEntity, Long>, JpaSpecificationExecutor<PodmiotyGospodarczeViewEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  boolean existsByWlascicielNipAndDataRozpoczecia(String wlascicielNip, LocalDate dataRozpoczecia);

  List<PodmiotyGospodarczeViewEntity> findAllByWlascicielNipIsNull();
}
