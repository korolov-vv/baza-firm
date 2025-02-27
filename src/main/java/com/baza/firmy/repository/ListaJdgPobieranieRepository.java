package com.baza.firmy.repository;

import com.baza.firmy.entity.ListaJdgPobieranie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaJdgPobieranieRepository extends JpaRepository<ListaJdgPobieranie, Long> {

  Optional<ListaJdgPobieranie> findFirstByCzyStareDaneOrderByIdDesc(boolean czyStareDane);
}
