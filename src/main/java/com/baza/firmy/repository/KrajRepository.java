package com.baza.firmy.repository;

import com.baza.firmy.entity.Kraj;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KrajRepository extends JpaRepository<Kraj, Long> {

  Optional<Kraj> findByKraj(String kraj);
}
