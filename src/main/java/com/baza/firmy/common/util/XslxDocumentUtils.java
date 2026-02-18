package com.baza.firmy.common.util;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.dto.PodmiotGospodarczyListDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class XslxDocumentUtils {

  public ByteArrayInputStream appendToExcel(ByteArrayOutputStream out, List<PodmiotGospodarczyListDto> jdgList, boolean isFirstPage, boolean isLastPage) {
    try (Workbook workbook = isFirstPage ? new XSSFWorkbook() : WorkbookFactory.create(new ByteArrayInputStream(out.toByteArray()))) {
      Sheet sheet = isFirstPage ? workbook.createSheet("Dane firm") : workbook.getSheetAt(0);

      CellStyle headerStyle = createHeaderStyle(workbook);
      CellStyle cellStyle = createCellStyle(workbook);

      String[] columns = {"Nazwa", "NIP", "REGON", "KRS", "Data rozpoczęcia działalności", "Email", "Telefon", "PKD główny", "PKD dodatkowe", "AdresEntity Korespondencyjny", "AdresEntity Działalności"};

      if (isFirstPage) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
          Cell cell = headerRow.createCell(i);
          cell.setCellValue(columns[i]);
          cell.setCellStyle(headerStyle);
        }
      }

      int rowNum = sheet.getLastRowNum() + 1;

      for (PodmiotGospodarczyListDto jdg : jdgList) {
        Row row = sheet.createRow(rowNum++);

        createCell(row, 0, jdg.getNazwa(), cellStyle);
        createCell(row, 1, jdg.getNip(), cellStyle);
        createCell(row, 2, jdg.getRegon(), cellStyle);
        createCell(row, 3, jdg.getKrs(), cellStyle);
        createCell(row, 4, jdg.getDataRozpoczecia(), cellStyle);
        createCell(row, 5, jdg.getEmail(), cellStyle);
        createCell(row, 6, jdg.getTelefon(), cellStyle);
        createCell(row, 7, jdg.getPkdGlowny().map(Pkd::getKod).orElse(Strings.EMPTY), cellStyle);
        createCell(row, 8, jdg.getPkd().stream().map(Pkd::getKod).collect(Collectors.joining(", ")), cellStyle);
        createCell(row, 9, jdg.getAdresKorespondencyjny().toString(), cellStyle);
        createCell(row, 10, jdg.getAdresDzialalnosci().map(AdresDto::toString).orElse(Strings.EMPTY), cellStyle);
      }

      for (int i = 0; i < columns.length; i++) {
        if (i != 4) {
          sheet.autoSizeColumn(i);
        }
      }

      if (isLastPage) {
        addBoldOutsideBorder(sheet, rowNum, columns.length);
      }

      out.reset();
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      log.error("Error generating Excel file: {}", e.getMessage());
      return new ByteArrayInputStream(new byte[0]);
    } catch (Exception e) {
      log.error("Unexpected error: {}", e.getMessage());
      return new ByteArrayInputStream(new byte[0]);
    }
  }

  private CellStyle createHeaderStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setBold(true);
    style.setFont(font);
    style.setBorderTop(BorderStyle.THICK);
    style.setBorderBottom(BorderStyle.THICK);
    style.setBorderLeft(BorderStyle.THICK);
    style.setBorderRight(BorderStyle.THICK);
    return style;
  }

  private CellStyle createCellStyle(Workbook workbook) {
    CellStyle style = workbook.createCellStyle();
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    return style;
  }

  private void createCell(Row row, int column, String value, CellStyle style) {
    Cell cell = row.createCell(column);
    cell.setCellValue(value);
    cell.setCellStyle(style);
  }

  private void addBoldOutsideBorder(Sheet sheet, int rowCount, int columnCount) {
    applyBoldTopBorder(sheet, columnCount);
    applyBoldBottomBorder(sheet, rowCount, columnCount);
    applyBoldLeftBorder(sheet, rowCount);
    applyBoldRightBorder(sheet, rowCount, columnCount);
  }

  private void applyBoldTopBorder(Sheet sheet, int columnCount) {
    for (int i = 0; i < columnCount; i++) {
      Cell topCell = sheet.getRow(0).getCell(i);
      topCell.getCellStyle().setBorderTop(BorderStyle.THICK);
    }
  }

  private void applyBoldBottomBorder(Sheet sheet, int rowCount, int columnCount) {
    for (int i = 0; i < columnCount; i++) {
      Cell bottomCell = sheet.getRow(rowCount - 1).getCell(i);
      bottomCell.getCellStyle().setBorderBottom(BorderStyle.THICK);
    }
  }

  private void applyBoldLeftBorder(Sheet sheet, int rowCount) {
    for (int i = 0; i < rowCount; i++) {
      if (i == 0) {
        Cell leftTopCell = sheet.getRow(i).getCell(0);
        leftTopCell.getCellStyle().setBorderTop(BorderStyle.THICK);
        leftTopCell.getCellStyle().setBorderLeft(BorderStyle.THICK);
      } else if (i == rowCount - 1) {
        Cell leftBottomCell = sheet.getRow(i).getCell(0);
        leftBottomCell.getCellStyle().setBorderBottom(BorderStyle.THICK);
        leftBottomCell.getCellStyle().setBorderLeft(BorderStyle.THICK);
      } else {
        Cell leftCell = sheet.getRow(i).getCell(0);
        leftCell.getCellStyle().setBorderTop(BorderStyle.THIN);
        leftCell.getCellStyle().setBorderLeft(BorderStyle.THICK);
      }
    }
  }

  private void applyBoldRightBorder(Sheet sheet, int rowCount, int columnCount) {
    for (int i = 0; i < rowCount; i++) {
      if (i == 0) {
        Cell leftTopCell = sheet.getRow(i).getCell(columnCount - 1);
        leftTopCell.getCellStyle().setBorderTop(BorderStyle.THICK);
        leftTopCell.getCellStyle().setBorderRight(BorderStyle.THICK);
      } else if (i == rowCount - 1) {
        Cell leftBottomCell = sheet.getRow(i).getCell(columnCount - 1);
        leftBottomCell.getCellStyle().setBorderBottom(BorderStyle.THICK);
        leftBottomCell.getCellStyle().setBorderRight(BorderStyle.THICK);
      } else {
        Cell leftCell = sheet.getRow(i).getCell(columnCount - 1);
        leftCell.getCellStyle().setBorderTop(BorderStyle.THIN);
        leftCell.getCellStyle().setBorderRight(BorderStyle.THIN);
      }
    }
  }
}
