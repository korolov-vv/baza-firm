package com.baza.firmy.repository;

import com.baza.firmy.entity.Jdg;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JdgRepository extends JpaRepository<Jdg, Long>, JpaSpecificationExecutor<Jdg> {

  boolean existsByCeidgId(UUID ceidgId);

  Optional<Jdg> findByCeidgId(UUID ceidgId);

  List<Jdg> findAllByWlascicielNipIsNull();
  List<Jdg> findAllByWlascicielIdIsNull();
}
