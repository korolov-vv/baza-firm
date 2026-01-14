package com.baza.firmy.firmycrm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface FirmyCrmRepository extends JpaRepository<FirmaCrmEntity, Long> {

  boolean existsByFirmaKlientUuidAndFirmaCrmUuid(UUID firmaKlientUuid, UUID firmaCrmUuid);

}
