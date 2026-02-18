package com.baza.firmy.danezportaluzewn.query;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PortalZewnQueryRepository extends JpaRepository<PortalZewnViewEntity, Long> {

  Optional<PortalZewnViewEntity> findFirstByStatusPobieraniaOrderByCreateDateDesc(StatusPobieraniaEnum status);

  boolean existsByStatusPobierania(StatusPobieraniaEnum status);
}
