package com.baza.firmy.firmysubscrypcje.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FirmySubscrypcjeRepository extends JpaRepository<FirmaSubscrypcjaEntity, Long> {

}
