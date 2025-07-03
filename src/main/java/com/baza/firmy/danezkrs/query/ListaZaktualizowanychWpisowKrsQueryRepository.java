package com.baza.firmy.danezkrs.query;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ListaZaktualizowanychWpisowKrsQueryRepository extends JpaRepository<ListaZaktualizowanychWpisowKrsViewEntity, Long> {

  boolean existsByStatusPobierania(StatusPobieraniaEnum statusPobierania);

  Optional<ListaZaktualizowanychWpisowKrsViewEntity> findFirstByCzyObsluzonaIsFalse();
}
