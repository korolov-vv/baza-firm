package com.baza.firmy.osoby.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OsobaRepository extends JpaRepository<OsobaEntity, Long> {

}
