package com.baza.firmy.danezportaluzewn.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface PortalZewnKategoriaRepository extends JpaRepository<PortalZewnKategoriaEntity, Long> {

  Optional<PortalZewnKategoriaEntity> findByUuid(UUID uuid);
}
