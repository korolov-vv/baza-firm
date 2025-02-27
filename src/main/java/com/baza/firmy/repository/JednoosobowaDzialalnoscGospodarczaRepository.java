package com.baza.firmy.repository;

import com.baza.firmy.entity.JednoosobowaDzialalnoscGospodarcza;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JednoosobowaDzialalnoscGospodarczaRepository extends JpaRepository<JednoosobowaDzialalnoscGospodarcza, Long> {

  boolean existsByCeidgId(UUID ceidgId);

}
