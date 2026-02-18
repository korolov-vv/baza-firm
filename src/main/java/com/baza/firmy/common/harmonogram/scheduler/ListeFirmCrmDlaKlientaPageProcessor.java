package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.firmycrm.domain.FirmyCrmFacade;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Serwis odpowiedzialny za tworzenie listy firm CRM dla klienta na podstawie parametrów subskrypcji.
 * Proces wykonywany jest asynchronicznie przez scheduler Quartz.
 */
@Slf4j
@RequiredArgsConstructor
@Service
class ListeFirmCrmDlaKlientaPageProcessor {

    private static final int PAGE_SIZE = 10;

    private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
    private final FirmyCrmFacade firmyCrmFacade;

    int processPages(UUID firmaKlientUuid,
                     Specification<PodmiotGospodarczyViewEntity> specification,
                     int iloscDostepnychFirm) {
        int pageNumber = 0;
        int totalCreated = 0;
        int remainingFirms = iloscDostepnychFirm;

        while (remainingFirms > 0 && totalCreated < iloscDostepnychFirm) {
            int currentPageSize = Math.min(PAGE_SIZE, remainingFirms);
            Pageable pageable = PageRequest.of(pageNumber, currentPageSize, Sort.by(Sort.Direction.DESC, "dataRozpoczecia"));

            log.debug("Pobieranie strony {} z {} rekordami", pageNumber, currentPageSize);

            Page<UUID> firmyUuidPage =
                    podmiotyGospodarczeQueryFacade.pobierzListePodmiotowGospodarczych(specification, pageable)
                            .map(PodmiotGospodarczyViewEntity::getUuid);

            List<UUID> firmyUuids = firmyUuidPage.getContent();

            if (firmyUuids.isEmpty()) {
                log.warn("Brak więcej firm spełniających kryteria. Pobrano łącznie: {}", totalCreated);
                break;
            }

            // Create FirmaCrm for current page
            List<UUID> utworzoneFirmyCrm = firmyCrmFacade.stworzFirmyCrm(firmaKlientUuid, firmyUuids);
            totalCreated += utworzoneFirmyCrm.size();
            remainingFirms -= utworzoneFirmyCrm.size();

            log.info("Utworzono {} firm CRM na stronie {}. Łącznie utworzono: {}/{}",
                    utworzoneFirmyCrm.size(), pageNumber, totalCreated, iloscDostepnychFirm);

            pageNumber++;

            // Safety break if no more pages
            if (!firmyUuidPage.hasNext()) {
                log.info("Osiągnięto ostatnią stronę wyników");
                break;
            }
        }

        return totalCreated;
    }
}

