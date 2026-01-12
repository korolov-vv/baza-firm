package com.baza.firmy.firmysubscrypcje.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ParametrySubscrypcjiRepository extends JpaRepository<ParametrySubscrypcjiEntity, Long> {

}
