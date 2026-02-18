package com.baza.firmy.kraje.query;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface KrajQueryRepository extends JpaRepository<KrajViewEntity, Long> {

  Optional<KrajViewEntity> findByUuid(UUID uuid);
}
