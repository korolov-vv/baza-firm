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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EksportujNoweFirmDoCrmSchrackService implements BazowySchedulerService {

    private final FirmySubscrypcjeQueryFacade firmySubscrypcjeQueryFacade;
    private final ListeFirmCrmDlaKlientaPageProcessor listeFirmCrmDlaKlientaPageProcessor;

    @Override
    public void executeScheduler(JobExecutionContext jobExecutionContext) {
        log.info("Start EKSPORTUJ_NOWE_FIRMY_DO_CRM_SCHRACK_SCHEDULER_JOB");
        ParametryWyszukiwaniaDto parametryWyszukiwaniaDto = ParametryWyszukiwaniaDto.builder()
                        .pkd("4321Z,2712Z,6110Z")
                        .dataRozpoczeciaOd(LocalDate.now().minusDays(6))
                        .dataRozpoczeciaDo(LocalDate.now().minusDays(3))
                        .build();

        try {
            List<FirmaSubscrypcjaViewEntity> firmySubscrypcje = firmySubscrypcjeQueryFacade.findAll();
            if (firmySubscrypcje.isEmpty()) {
                throw new IllegalArgumentException("Nie znaleziono List<FirmaSubscrypcjaViewEntity>");
            }

            FirmaSubscrypcjaViewEntity firmaSubscrypcja = firmySubscrypcje.stream()
                    .filter(firmaSubscrypcjaViewEntity -> firmaSubscrypcjaViewEntity.getFirmaKlienta().getNip().equals("5240018605"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono FirmaSubscrypcjaViewEntity z NIPem równym 5240018605"));

            PodmiotGospodarczyViewEntity firmaKlient = firmaSubscrypcja.getFirmaKlienta();
            int iloscDostepnychFirm = firmaSubscrypcja.getSubscrypcja().getIloscDostepnychFirm();

            Specification<PodmiotGospodarczyViewEntity> specification = createSpecification(parametryWyszukiwaniaDto);
            int totalCreated = listeFirmCrmDlaKlientaPageProcessor.processPages(firmaKlient.getUuid(), specification, iloscDostepnychFirm);

            log.info("Zakończono tworzenie listy firm CRM. Utworzono łącznie {} firm dla klienta: {}",
                    totalCreated, firmaKlient.getUuid());
        } catch (Exception e) {
            log.error("Błąd podczas tworzenia listy firm CRM dla Schrack", e);
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
