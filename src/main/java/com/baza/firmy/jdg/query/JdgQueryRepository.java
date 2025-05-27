package com.baza.firmy.jdg.query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JdgQueryRepository extends JpaRepository<JdgViewEntity, Long>, JpaSpecificationExecutor<JdgViewEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  boolean existsByWlascicielNipAndNazwaAndDataRozpoczecia(String wlascicielNip, String nazwa, LocalDate dataRozpoczecia);

  List<JdgViewEntity> findAllByWlascicielNipIsNull();
}
