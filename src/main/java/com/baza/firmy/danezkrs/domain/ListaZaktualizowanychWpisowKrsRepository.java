package com.baza.firmy.danezkrs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ListaZaktualizowanychWpisowKrsRepository extends JpaRepository<ListaZaktualizowanychWpisowKrsEntity, Long> {
}
