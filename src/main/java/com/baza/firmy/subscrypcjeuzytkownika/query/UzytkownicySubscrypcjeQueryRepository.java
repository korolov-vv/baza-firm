package com.baza.firmy.subscrypcjeuzytkownika.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface UzytkownicySubscrypcjeQueryRepository extends JpaRepository<UzytkownikSubscrypcjaViewEntity, Long> {

    List<UzytkownikSubscrypcjaViewEntity> findAllByFirmaKlientaUuidAndStatusSubscrypcjiOrderByAktywnaDoDesc(UUID uuidFirmyKlienta, StatusSubscrypcji statusSubscrypcji);
}
