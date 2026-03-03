package com.baza.firmy.firmycrm.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
interface FirmyCrmQueryRepository extends JpaRepository<FirmaCrmViewEntity, Long>, JpaSpecificationExecutor<FirmaCrmViewEntity> {

  Optional<FirmaCrmViewEntity> findByFirmaKlientUuidAndUuid(UUID firmaKlientUuid, UUID firmaCrmUuid);

  @Query("select f from FirmaCrmViewEntity f " +
          "where f.firmaKlient.uuid = :uuid " +
          "and (f.dataNastepnegoKontaktu = :data or f.createDate = :data)")
  Page<FirmaCrmViewEntity> znajdzFirmyDoKontaktu(@Param("uuid") UUID uuid, @Param("data") LocalDateTime data, Pageable pageable);

  @Query("select count(f) > 0 from FirmaCrmViewEntity f " +
          "where f.firmaKlient.uuid = :uuid " +
          "and (f.dataNastepnegoKontaktu = :data or f.createDate = :data)")
  boolean czySaFirmyDoKontaktu(@Param("uuid") UUID uuid, @Param("data") LocalDateTime data);

}
