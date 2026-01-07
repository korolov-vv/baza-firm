package com.baza.firmy.uzytkownicy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UzytkownicyRepository extends JpaRepository<UzytkownikEntity, Long> {

}
