package com.baza.firmy.danezportaluzewn.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PortalZewnRepository extends JpaRepository<PortalZewnEntity, Long> {

  Optional<PortalZewnEntity> findByUuid(UUID uuid);
}
