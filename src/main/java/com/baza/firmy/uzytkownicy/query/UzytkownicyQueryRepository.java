package com.baza.firmy.uzytkownicy.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface UzytkownicyQueryRepository extends JpaRepository<UzytkownikViewEntity, Long> {

    Optional<UzytkownikViewEntity> findByUuid(UUID uuid);

    Optional<UzytkownikViewEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
