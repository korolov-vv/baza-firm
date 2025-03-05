package com.baza.firmy.repository;

import com.baza.firmy.entity.Pkd;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PkdRepository extends JpaRepository<Pkd, Long> {

  Optional<Pkd> findByKod(String pkd);
}
