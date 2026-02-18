package com.baza.firmy.subscrypcje.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface SubscrypcjeQueryRepository extends JpaRepository<SubscrypcjaViewEntity, Long> {

    Optional<SubscrypcjaViewEntity> findByUuid(UUID uuid);

    Optional<SubscrypcjaViewEntity> findByNazwa(String email);
}
