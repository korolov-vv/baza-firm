package com.baza.firmy.danezkrs.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ListaZaktualizowanychWpisowKrsRepository extends JpaRepository<ListaZaktualizowanychWpisowKrsEntity, Long> {

  Optional<ListaZaktualizowanychWpisowKrsEntity> findByUuid(UUID uuid);
}
