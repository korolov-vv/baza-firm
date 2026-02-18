package com.baza.firmy.firmysubscrypcje.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface FirmySubscrypcjeQueryRepository extends JpaRepository<FirmaSubscrypcjaViewEntity, Long> {

    List<FirmaSubscrypcjaViewEntity> findAllByFirmaKlientaUuidAndStatusSubscrypcjiOrderByAktywnaDoDesc(UUID uuidFirmyKlienta, StatusSubscrypcji statusSubscrypcji);

    @Query("SELECT fs FROM FirmaSubscrypcjaViewEntity fs " +
            "JOIN fs.firmaKlienta fk " +
            "JOIN fs.subscrypcja " +
            "LEFT OUTER JOIN fs.parametrySubscrypcji " +
            "WHERE fs.uuid = ?1")
    Optional<FirmaSubscrypcjaViewEntity> findByUuidPelneInfo(UUID uuid);
}
