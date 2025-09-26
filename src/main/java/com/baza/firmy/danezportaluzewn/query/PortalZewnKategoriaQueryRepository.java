package com.baza.firmy.danezportaluzewn.query;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface PortalZewnKategoriaQueryRepository extends JpaRepository<PortalZewnKategoriaViewEntity, Long> {

  Optional<PortalZewnKategoriaViewEntity> findByUuid(UUID uuid);
  Optional<PortalZewnKategoriaViewEntity> findFirstByStatusPobierania(StatusPobieraniaEnum status);

  boolean existsByStatusPobierania(StatusPobieraniaEnum statusPobieraniaEnum);
}
