package com.baza.firmy.subscrypcje.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SubscrypcjeRepository extends JpaRepository<SubscrypcjaEntity, Long> {

}
