package com.baza.firmy.podmiotygospodarcze.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PodmiotyGospodarczeRepository extends JpaRepository<PodmiotyGospodarczeEntity, Long>, JpaSpecificationExecutor<PodmiotyGospodarczeEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  Optional<PodmiotyGospodarczeEntity> findByCeidgId(UUID ceidgId);

  List<PodmiotyGospodarczeEntity> findAllByWlascicielNipIsNull();
}
