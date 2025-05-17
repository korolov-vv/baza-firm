package com.baza.firmy.jdg.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface JdgRepository extends JpaRepository<JdgEntity, Long>, JpaSpecificationExecutor<JdgEntity> {

  boolean existsByCeidgId(UUID ceidgId);

  Optional<JdgEntity> findByCeidgId(UUID ceidgId);

  List<JdgEntity> findAllByWlascicielNipIsNull();
}
