package com.baza.firmy.repository;

import com.baza.firmy.entity.Jdg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JdgRepository extends JpaRepository<Jdg, Long>, JpaSpecificationExecutor<Jdg> {

  boolean existsByCeidgId(UUID ceidgId);

  Optional<Jdg> findByCeidgId(UUID ceidgId);

}
