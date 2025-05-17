package com.baza.firmy.pkd.query;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PkdQueryRepository extends JpaRepository<PkdViewEntity, Long> {

  Optional<PkdViewEntity> findByKod(String pkd);

  Optional<PkdViewEntity> findByUuid(UUID uuid);
}
