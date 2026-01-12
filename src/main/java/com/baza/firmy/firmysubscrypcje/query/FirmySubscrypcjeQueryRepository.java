package com.baza.firmy.firmysubscrypcje.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface FirmySubscrypcjeQueryRepository extends JpaRepository<FirmaSubscrypcjaViewEntity, Long> {

    List<FirmaSubscrypcjaViewEntity> findAllByFirmaKlientaUuidAndStatusSubscrypcjiOrderByAktywnaDoDesc(UUID uuidFirmyKlienta, StatusSubscrypcji statusSubscrypcji);
}
