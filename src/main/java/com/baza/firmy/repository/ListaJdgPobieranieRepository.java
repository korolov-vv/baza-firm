package com.baza.firmy.repository;

import com.baza.firmy.entity.ListaJdgPobieranie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaJdgPobieranieRepository extends JpaRepository<ListaJdgPobieranie, Long> {

  Optional<ListaJdgPobieranie> findFirstByCzyStareDaneOrderByIdDesc(boolean czyStareDane);

  Page<ListaJdgPobieranie> findAllByCzyObsluzonaIsFalseAndCzyStareDaneIsFalse(Pageable pageable);

  Page<ListaJdgPobieranie> findAllByCzyObsluzonaIsFalseAndCzyStareDaneIsTrueOrderByIdDesc(Pageable pageable);

}
