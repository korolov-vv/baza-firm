package com.baza.firmy.danezraportu.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DaneZRaportuRepository extends JpaRepository<DaneZRaportuEntity, Long> {

  Optional<DaneZRaportuEntity> findByWojewodztwo(String wojewodztwo);
}
