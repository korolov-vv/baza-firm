package com.baza.firmy.firmycrm.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface FirmyCrmQueryRepository extends JpaRepository<FirmaCrmViewEntity, Long>, JpaSpecificationExecutor<FirmaCrmViewEntity> {

  Optional<FirmaCrmViewEntity> findByFirmaKlientUuidAndUuid(UUID firmaKlientUuid, UUID firmaCrmUuid);

}
