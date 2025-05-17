package com.baza.firmy.adresy.query;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AdresQueryRepository extends JpaRepository<AdresViewEntity, Long> {

  Optional<AdresViewEntity> findByUuid(UUID uuid);
}
