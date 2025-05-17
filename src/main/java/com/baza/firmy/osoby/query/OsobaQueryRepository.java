package com.baza.firmy.osoby.query;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OsobaQueryRepository extends JpaRepository<OsobaViewEntity, Long> {

   Optional<OsobaViewEntity> findByUuid(UUID uuid);

   Optional<OsobaViewEntity> findByNip(String nip);
}
