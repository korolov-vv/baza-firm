package com.baza.firmy.service;

import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import com.baza.firmy.entity.Adres;
import com.baza.firmy.entity.Jdg;
import com.baza.firmy.entity.Kraj;
import com.baza.firmy.entity.Osoba;
import com.baza.firmy.entity.Pkd;
import com.baza.firmy.mapper.JdgMapper;
import com.baza.firmy.repository.AdresRepository;
import com.baza.firmy.repository.JdgFilterSpecification;
import com.baza.firmy.repository.JdgRepository;
import com.baza.firmy.repository.KrajRepository;
import com.baza.firmy.repository.OsobaRepository;
import com.baza.firmy.repository.PkdRepository;
import com.baza.firmy.response.JdgSzczegolyDto;
import com.baza.firmy.util.FileUtills;
import com.baza.firmy.util.XslxDocumentUtils;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JdgService {

  private final JdgMapper jdgMapper;
  private final JdgRepository jdgRepository;
  private final AdresRepository adresRepository;
  private final OsobaRepository osobaRepository;
  private final PkdRepository pkdRepository;
  private final KrajRepository krajRepository;
  private final XslxDocumentUtils xslxDocumentUtils;
  private final FileUtills fileUtills;

  public Page<JdgListDto> pobierzListeJdg(Specification<Jdg> specification, Pageable pageable) {
    return jdgRepository.findAll(specification, pageable)
        .map(jdgMapper::toJdgListDtoList);
  }

  // TODO: Implement some generic exporting
  @Transactional
  public void exportujDoXlsx(ParametryWyszukiwaniaDto parametry) {
    Specification<Jdg> specification = createSpecification(parametry);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    readDataAndSaveToFile(specification, out);
  }

  @Transactional(TxType.REQUIRES_NEW)
  public UUID zapiszSzczegolyJdg(JdgSzczegolyDto jdgSzczegolyDto) {
    Jdg doZapisu = jdgRepository.findByCeidgId(jdgSzczegolyDto.getCeidgId())
        .map(jdg -> jdgMapper.toJdgEntity(jdg, jdgSzczegolyDto))
        .orElseGet(() -> jdgMapper.toJdgEntity(jdgSzczegolyDto));

    if (doZapisu.getId() == null) {
      przygotujDoZapisu(doZapisu);
    }

    Jdg saved = jdgRepository.saveAndFlush(doZapisu);

    log.info("Zapisano JDG: {}", saved.getCeidgId());

    return saved.getUuid();
  }

  public boolean czyIstniejePoCeidgId(UUID ceidgId) {
    return jdgRepository.existsByCeidgId(ceidgId);
  }

  public List<String> pobierzLinkiDoJdgBezNipow() {
    return jdgRepository.findAllByWlascicielNipIsNull().stream()
        .map(Jdg::getLink)
        .toList();
  }

  private static Specification<Jdg> createSpecification(ParametryWyszukiwaniaDto parametry) {
    return SpecificationBuilder.specification(
            JdgFilterSpecification.class)
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

  private void readDataAndSaveToFile(Specification<Jdg> specification, ByteArrayOutputStream out) {
    int pageNumber = 0;
    int pageSize = 1000;
    Page<JdgListDto> page;

    do {
      fileUtills.readFromFile(out, "Jdg_list_" + LocalDate.now() + ".xlsx");
      page = fetchData(specification, pageNumber, pageSize);
      fileUtills.saveToFile(
          xslxDocumentUtils.appendToExcel(out, page.getContent(), pageNumber == 0,
              pageNumber == page.getTotalPages()), "Jdg_list_" + LocalDate.now() + ".xlsx");
      pageNumber++;
    } while (page.hasNext());
  }

  private Page<JdgListDto> fetchData(Specification<Jdg> specification, int pageNumber,
      int pageSize) {
    Page<JdgListDto> page;
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    page = jdgRepository.findAll(specification, pageable)
        .map(jdgMapper::toJdgListDtoList);
    return page;
  }

  private void przygotujDoZapisu(Jdg doZapisu) {
    zaktualizujAdresy(doZapisu);
    zaktualizujWlasciciela(doZapisu);
    zaktualizujKodyPkd(doZapisu);
  }

  private void zaktualizujAdresy(Jdg doZapisu) {
    Adres adresDzialalnosciZapisany = adresRepository.save(doZapisu.getAdresDzialalnosci());
    Adres adresKorespondencyjnyZapisany = adresRepository.save(doZapisu.getAdresKorespondencyjny());

    doZapisu.setAdresDzialalnosci(adresDzialalnosciZapisany);
    doZapisu.setAdresKorespondencyjny(adresKorespondencyjnyZapisany);
  }

  private void zaktualizujWlasciciela(Jdg doZapisu) {
    if (doZapisu.getWlasciciel().getId() == null && doZapisu.getWlasciciel().getNip() != null) {
      osobaRepository.findByNip(doZapisu.getWlasciciel().getNip())
          .ifPresentOrElse(doZapisu::setWlasciciel, zapiszWlasciciela(doZapisu));
    } else {
      zapiszWlasciciela(doZapisu);
    }
  }

  Runnable zapiszWlasciciela(Jdg doZapisu) {
    return () -> {
      List<Kraj> krajeZapisane = zapiszKraje(doZapisu);
      doZapisu.getWlasciciel().setObywatelstwa(krajeZapisane);

      Osoba wlascicielZapisany = osobaRepository.save(doZapisu.getWlasciciel());
      doZapisu.setWlasciciel(wlascicielZapisany);
    };
  }

  private List<Kraj> zapiszKraje(Jdg doZapisu) {
    return doZapisu.getWlasciciel().getObywatelstwa().stream()
        .map(kraj -> {
          if (kraj.getKraj() != null) {
            return krajRepository.findByKraj(kraj.getKraj())
                .orElseGet(() -> krajRepository.save(kraj));
          } else {
            return kraj;
          }
        })
        .toList();
  }

  private void zaktualizujKodyPkd(Jdg doZapisu) {
    zaktualizujPkdGlowny(doZapisu);
    zaktualizujPkdDodatkowe(doZapisu);
  }

  private void zaktualizujPkdGlowny(Jdg doZapisu) {
    if (doZapisu.getPkdGlowny() == null || doZapisu.getPkdGlowny().getKod() == null) {
      return;
    }
    if (doZapisu.getWlasciciel().getId() == null) {
      pkdRepository.findByKod(doZapisu.getPkdGlowny().getKod()).ifPresentOrElse(doZapisu::setPkdGlowny,
          () -> {
            Pkd pkdGlownyZapisany = pkdRepository.save(doZapisu.getPkdGlowny());
            doZapisu.setPkdGlowny(pkdGlownyZapisany);
          });
    } else {
      doZapisu.setPkdGlowny(pkdRepository.save(doZapisu.getPkdGlowny()));
    }
  }

  private void zaktualizujPkdDodatkowe(Jdg doZapisu) {
    if (doZapisu.getPkd() == null || doZapisu.getPkd().isEmpty()) {
      return;
    }

    List<Pkd> pkdZapisane = doZapisu.getPkd().stream()
        .filter(pkd -> pkd.getKod() != null && !pkd.getKod().equals(doZapisu.getPkdGlowny().getKod()))
        .map(pkd -> {
          if (pkd.getId() == null) {
            return pkdRepository.findByKod(pkd.getKod())
                .orElseGet(() -> pkdRepository.save(pkd));
          } else {
            return pkdRepository.save(pkd);
          }
        })
        .toList();

    doZapisu.setPkd(pkdZapisane);
  }
}
