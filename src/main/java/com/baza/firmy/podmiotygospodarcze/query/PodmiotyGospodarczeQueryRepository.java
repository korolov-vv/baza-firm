package com.baza.firmy.podmiotygospodarcze.query;

import com.baza.firmy.constants.enums.BusinessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface PodmiotyGospodarczeQueryRepository extends JpaRepository<PodmiotGospodarczyViewEntity, Long>, JpaSpecificationExecutor<PodmiotGospodarczyViewEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  boolean existsByNipAndDataRozpoczecia(String nip, LocalDate dataRozpoczecia);

  List<PodmiotGospodarczyViewEntity> findAllByWlascicielNipIsNull();

  boolean existsByNumerKrs(String krs);

  List<PodmiotGospodarczyViewEntity> findAllByNipAndStatus(String nip, BusinessStatus status);

  boolean existsByNipAndStatus(String nip, BusinessStatus status);

  Optional<PodmiotGospodarczyViewEntity> findByUuid(UUID uuid);
}
