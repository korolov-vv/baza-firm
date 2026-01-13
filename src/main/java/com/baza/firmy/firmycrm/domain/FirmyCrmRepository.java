package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FirmyCrmRepository extends JpaRepository<FirmaCrmEntity, Long> {

  boolean existsByFirmaKlientAndFirmaCrm(PodmiotGospodarczyViewEntity firmaKlient, PodmiotGospodarczyViewEntity firmaCrm);

}
