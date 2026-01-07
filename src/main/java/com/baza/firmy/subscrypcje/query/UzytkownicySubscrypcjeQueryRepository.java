package com.baza.firmy.subscrypcje.query;

import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface UzytkownicySubscrypcjeQueryRepository extends JpaRepository<UzytkownikSubscrypcjaViewEntity, Long> {

    Optional<UzytkownikSubscrypcjaViewEntity> findByUzytkownikUuidAndStatusSubscrypcji(UUID uuidUzytkownika, StatusSubscrypcji statusSubscrypcji);
}
