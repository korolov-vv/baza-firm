package com.baza.firmy.firmysubscrypcje.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface FirmySubscrypcjeRepository extends JpaRepository<FirmaSubscrypcjaEntity, Long> {

  Optional<FirmaSubscrypcjaEntity> findByUuid(UUID uuid);

}
