package com.baza.firmy.pkd.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PkdRepository extends JpaRepository<PkdEntity, Long> {

  Optional<PkdEntity> findByKod(String pkd);
}
