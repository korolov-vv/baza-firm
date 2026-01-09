package com.baza.firmy.uzytkownicy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UzytkownicyRepository extends JpaRepository<UzytkownikEntity, Long> {

    boolean existsByFirmaNip(String nip);

    Optional<UzytkownikEntity> findByEmail(String email);
}
