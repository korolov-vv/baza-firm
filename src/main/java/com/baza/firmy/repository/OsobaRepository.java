package com.baza.firmy.repository;

import com.baza.firmy.entity.Osoba;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OsobaRepository extends JpaRepository<Osoba, Long> {

  Optional<Osoba> findByNip(String nip);
}
