package com.baza.firmy.subscrypcjeuzytkownika.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ParametrySubscrypcjiQueryRepository extends JpaRepository<ParametrySubscrypcjiViewEntity, Long> {

}
