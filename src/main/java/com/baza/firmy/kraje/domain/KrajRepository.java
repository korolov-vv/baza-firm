package com.baza.firmy.kraje.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface KrajRepository extends JpaRepository<KrajEntity, Long> {

  Optional<KrajEntity> findByKraj(String kraj);
}
