package com.baza.firmy.adresy.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AdresRepository extends JpaRepository<AdresEntity, Long> {
}
