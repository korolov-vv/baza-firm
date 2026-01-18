package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.firmycrm.domain.FirmyCrmFacade;
import com.baza.firmy.firmysubscrypcje.query.FirmaSubscrypcjaViewEntity;
import com.baza.firmy.firmysubscrypcje.query.FirmySubscrypcjeQueryFacade;
import com.baza.firmy.firmysubscrypcje.query.ParametrySubscrypcjiViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeFilterSpecification;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.quartz.JobExecutionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Serwis odpowiedzialny za tworzenie listy firm CRM dla klienta na podstawie parametrów subskrypcji.
 * Proces wykonywany jest asynchronicznie przez scheduler Quartz.
 */
@Slf4j
@RequiredArgsConstructor
@Service
class StworzListeFirmCrmDlaKlientaService implements BazowySchedulerService {

    private static final int PAGE_SIZE = 10;
    private static final String FIRMA_SUBSCRYPCJA_UUID_KEY = "firmaSubscrypcjaUuid";

    private final FirmySubscrypcjeQueryFacade firmySubscrypcjeQueryFacade;
    private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
    private final FirmyCrmFacade firmyCrmFacade;

    /**
     * Główna metoda wykonująca proces tworzenia listy firm CRM dla klienta.
     *
     * @param jobExecutionContext kontekst wykonania zadania Quartz zawierający UUID subskrypcji
     */
    @Override
    @Async("stworzListeFirmCrmDlaKlienta")
    public void executeScheduler(JobExecutionContext jobExecutionContext) {
        // 1. Get UUID FirmaSubscrypcja from JobDataMap
        UUID firmaSubscrypcjaUuid = (UUID) jobExecutionContext.getMergedJobDataMap().get(FIRMA_SUBSCRYPCJA_UUID_KEY);

        if (firmaSubscrypcjaUuid == null) {
            log.error("Brak UUID FirmaSubscrypcja w JobDataMap");
            return;
        }

        log.info("Rozpoczęcie tworzenia listy firm CRM dla subscrypcji: {}", firmaSubscrypcjaUuid);

        try {
            // 2. Get FirmaSubscrypcja
            FirmaSubscrypcjaViewEntity firmaSubscrypcja = firmySubscrypcjeQueryFacade.findByUuidPelneInfo(firmaSubscrypcjaUuid)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono FirmaSubscrypcja o UUID: " + firmaSubscrypcjaUuid));

            // 3. Get number of firms from Subscription
            int iloscDostepnychFirm = firmaSubscrypcja.getSubscrypcja().getIloscDostepnychFirm();
            log.info("Liczba dostępnych firm w subskrypcji: {}", iloscDostepnychFirm);

            if (iloscDostepnychFirm <= 0) {
                log.warn("Liczba dostępnych firm jest <= 0. Zakończono bez tworzenia firm CRM.");
                return;
            }

            // 4. Get firma klient and validate
            PodmiotGospodarczyViewEntity firmaKlient = firmaSubscrypcja.getFirmaKlienta();
            if (firmaKlient == null) {
                log.error("Brak firmy klienta dla subskrypcji: {}", firmaSubscrypcjaUuid);
                throw new IllegalStateException("FirmaKlient nie może być null dla subskrypcji: " + firmaSubscrypcjaUuid);
            }

            // 5. Create specification and process pages
            Specification<PodmiotGospodarczyViewEntity> specification = createSpecification(firmaSubscrypcja);
            int totalCreated = processPages(firmaKlient.getUuid(), specification, iloscDostepnychFirm);

            log.info("Zakończono tworzenie listy firm CRM. Utworzono łącznie {} firm dla klienta: {}",
                    totalCreated, firmaKlient.getUuid());
        } catch (Exception e) {
            log.error("Błąd podczas tworzenia listy firm CRM dla subscrypcji: {}", firmaSubscrypcjaUuid, e);
            throw e;
        }
    }

    private int processPages(UUID firmaKlientUuid,
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

    private Specification<PodmiotGospodarczyViewEntity> createSpecification(FirmaSubscrypcjaViewEntity firmaSubscrypcja) {
        SpecificationBuilder<PodmiotyGospodarczeFilterSpecification> builder = SpecificationBuilder.specification(
                PodmiotyGospodarczeFilterSpecification.class);
        ParametrySubscrypcjiViewEntity parametry = firmaSubscrypcja.getParametrySubscrypcji();

        if (parametry != null) {
            parametry.getPkd().ifPresent(pkd -> builder.withParam("pkd", pkd));

            parametry.getDataRozpoczeciaOd().ifPresent(data ->
                    builder.withParam("dataRozpoczeciaOd", data.format(DateTimeFormatter.ISO_DATE)));

            parametry.getDataRozpoczeciaDo().ifPresent(data ->
                    builder.withParam("dataRozpoczeciaDo", data.format(DateTimeFormatter.ISO_DATE)));

            parametry.getWojewodztwo().ifPresent(woj -> builder.withParam("wojewodztwo", woj));

            parametry.getPowiat().ifPresent(pow -> builder.withParam("powiat", pow));

            parametry.getGmina().ifPresent(gm -> builder.withParam("gmina", gm));
        }

        builder.withParam("status", BusinessStatus.AKTYWNY.name());
        return builder.build();
    }
}

