package com.baza.firmy.common.harmonogram.scheduler;

import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import com.baza.firmy.firmysubscrypcje.query.FirmaSubscrypcjaViewEntity;
import com.baza.firmy.firmysubscrypcje.query.FirmySubscrypcjeQueryFacade;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeFilterSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.quartz.JobExecutionContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
class EksportujNoweFirmDoCrmSchrackService implements BazowySchedulerService {

    private static final String PARAMETRY_WYSZUKIWANIA_KLUCZ = "parametryWyszukawania";
    private static final String FIRMA_SUBSCRYPCJA_UUID_KLUCZ = "firmaSubscrypcjaUuid";

    private final FirmySubscrypcjeQueryFacade firmySubscrypcjeQueryFacade;
    private final ListeFirmCrmDlaKlientaPageProcessor listeFirmCrmDlaKlientaPageProcessor;

    @Override
    public void executeScheduler(JobExecutionContext jobExecutionContext) {
        log.info("Start EKSPORTUJ_NOWE_FIRMY_DO_CRM_SCHRACK_SCHEDULER_JOB");
        ParametryWyszukiwaniaDto parametryWyszukiwaniaDto = (ParametryWyszukiwaniaDto) jobExecutionContext.getMergedJobDataMap().get(PARAMETRY_WYSZUKIWANIA_KLUCZ);
        UUID firmaSubscrypcjaUuid = (UUID) jobExecutionContext.getMergedJobDataMap().get(FIRMA_SUBSCRYPCJA_UUID_KLUCZ);

        try {
            FirmaSubscrypcjaViewEntity firmaSubscrypcja = firmySubscrypcjeQueryFacade.findByUuidPelneInfo(firmaSubscrypcjaUuid)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono FirmaSubscrypcja o UUID: " + firmaSubscrypcjaUuid));

            PodmiotGospodarczyViewEntity firmaKlient = firmaSubscrypcja.getFirmaKlienta();
            int iloscDostepnychFirm = firmaSubscrypcja.getSubscrypcja().getIloscDostepnychFirm();

            Specification<PodmiotGospodarczyViewEntity> specification = createSpecification(parametryWyszukiwaniaDto);
            int totalCreated = listeFirmCrmDlaKlientaPageProcessor.processPages(firmaKlient.getUuid(), specification, iloscDostepnychFirm);

            log.info("Zakończono tworzenie listy firm CRM. Utworzono łącznie {} firm dla klienta: {}",
                    totalCreated, firmaKlient.getUuid());
        } catch (Exception e) {
            log.error("Błąd podczas tworzenia listy firm CRM dla subscrypcji: {}", firmaSubscrypcjaUuid, e);
            throw e;
        }

        log.info("Complete EKSPORTUJ_NOWE_FIRMY_DO_CRM_SCHRACK_SCHEDULER_JOB");
    }

    private static Specification<PodmiotGospodarczyViewEntity> createSpecification(ParametryWyszukiwaniaDto parametry) {
        return SpecificationBuilder.specification(
                        PodmiotyGospodarczeFilterSpecification.class)
                .withParam("nazwa", parametry.getNazwa())
                .withParam("pkd", parametry.getPkd() != null ? parametry.getPkd() : "")
                .withParam("createDate",
                        parametry.getCreateDate() != null ? parametry.getCreateDate()
                                .format(DateTimeFormatter.ISO_DATE_TIME) : null)
                .withParam("dataRozpoczeciaOd",
                        parametry.getDataRozpoczeciaOd() != null ? parametry.getDataRozpoczeciaOd()
                                .format(DateTimeFormatter.ISO_DATE) : null)
                .withParam("dataRozpoczeciaDo",
                        parametry.getDataRozpoczeciaDo() != null ? parametry.getDataRozpoczeciaDo()
                                .format(DateTimeFormatter.ISO_DATE) : null)
                .withParam("status", parametry.getStatus())
                .withParam("wojewodztwo", parametry.getWojewodztwo())
                .withParam("powiat", parametry.getPowiat())
                .withParam("gmina", parametry.getGmina())
                .build();
    }
}
