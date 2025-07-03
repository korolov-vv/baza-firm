package com.baza.firmy.podmiotygospodarcze.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface PodmiotyGospodarczeRepository extends JpaRepository<PodmiotGospodarczeEntity, Long>, JpaSpecificationExecutor<PodmiotGospodarczeEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  Optional<PodmiotGospodarczeEntity> findByCeidgId(UUID ceidgId);

  List<PodmiotGospodarczeEntity> findAllByWlascicielNipIsNull();

  Optional<PodmiotGospodarczeEntity> findByNumerKrs(String krs);
}
