package com.baza.firmy.repository;

import com.baza.firmy.entity.Jdg;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdgRepository extends JpaRepository<Jdg, Long> {

  boolean existsByCeidgId(UUID ceidgId);
  Optional<Jdg> findByCeidgId(UUID ceidgId);

}
