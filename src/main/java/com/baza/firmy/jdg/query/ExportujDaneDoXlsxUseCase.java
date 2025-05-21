package com.baza.firmy.jdg.query;

import com.baza.firmy.common.util.FileUtills;
import com.baza.firmy.common.util.XslxDocumentUtils;
import com.baza.firmy.dto.FileDto;
import com.baza.firmy.dto.JdgListDto;
import com.baza.firmy.dto.ParametryWyszukiwaniaDto;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportujDaneDoXlsxUseCase {

  private final JdgQueryRepository jdgQueryRepository;
  private final JdgQueryMapper jdgQueryMapper;
  private final XslxDocumentUtils xslxDocumentUtils;
  private final FileUtills fileUtills;

  // TODO: Implement some generic exporting
  public void exportujDoXlsx(ParametryWyszukiwaniaDto parametry) {
    readDataAndSaveToFile(
        createSpecification(parametry),
        createFileDto(parametry)
    );
  }

  private static FileDto createFileDto(ParametryWyszukiwaniaDto parametry) {
    FileDto fileDto = new FileDto();
    fileDto.setVersion(0);
    fileDto.setFileName("Jdg_list_" + parametry.getDataRozpoczeciaOd() + ".xlsx");
    fileDto.setPath("schrack/" + LocalDate.now().minusDays(3).getYear() + "/" + LocalDate.now().minusDays(3).getMonth());
    fileDto.setExtention("XLSX");
    fileDto.setSize(0L);
    return fileDto;
  }

  private static Specification<JdgViewEntity> createSpecification(ParametryWyszukiwaniaDto parametry) {
    return SpecificationBuilder.specification(
            JdgFilterSpecification.class)
        .withParam("nazwa", parametry.getNazwa())
        .withParam("pkdEntity", parametry.getPkd() != null ? parametry.getPkd() : "")
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

  private void readDataAndSaveToFile(Specification<JdgViewEntity> specification, FileDto fileDto) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int pageNumber = 0;
    int pageSize = 1000;
    Page<JdgListDto> page;

    do {
      page = fetchData(specification, pageNumber, pageSize);
      if (pageNumber > 0) {
        fileUtills.readFromFile(out, fileDto.getPath(), fileDto.getFileName());
      }
      fileDto = fileUtills.saveToFile(xslxDocumentUtils.appendToExcel(out, page.getContent(),
              pageNumber == 0, pageNumber == page.getTotalPages()),
          fileDto);
      pageNumber++;
    } while (page.hasNext());
  }

  private Page<JdgListDto> fetchData(Specification<JdgViewEntity> specification, int pageNumber,
                                     int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    return jdgQueryRepository.findAll(specification, pageable)
        .map(jdgQueryMapper::toJdgListDtoList);
  }
}
