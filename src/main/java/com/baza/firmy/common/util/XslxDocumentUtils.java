package com.baza.firmy.common.util;

import com.baza.firmy.adresy.domain.dto.AdresDto;
import com.baza.firmy.dto.PodmiotGospodarczyListDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

  private static final String[] COLUMNS = {
      "Nazwa", "NIP", "REGON", "KRS", "Data rozpoczęcia działalności",
      "Email", "Telefon", "PKD główny", "PKD dodatkowe",
      "AdresEntity Korespondencyjny", "AdresEntity Działalności"
  };
  // Fixed column widths in units of 1/256 of a character width
  private static final int[] COLUMN_WIDTHS = {
      12000, 4000, 5000, 6000, 8000, 8000, 5000, 4000, 12000, 14000, 14000
  };
  // Row window kept in memory at a time; older rows are flushed to a temp file
  private static final int STREAMING_ROW_WINDOW = 500;

  /**
   * Creates a new streaming workbook with a header row.
   * The caller is responsible for calling {@link #finalizeWorkbook} when done.
   */
  public SXSSFWorkbook createStreamingWorkbook() {
    SXSSFWorkbook workbook = new SXSSFWorkbook(STREAMING_ROW_WINDOW);
    Sheet sheet = workbook.createSheet("Dane firm");

    CellStyle headerStyle = createHeaderStyle(workbook);
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < COLUMNS.length; i++) {
      Cell cell = headerRow.createCell(i);
      cell.setCellValue(COLUMNS[i]);
      cell.setCellStyle(headerStyle);
      sheet.setColumnWidth(i, COLUMN_WIDTHS[i]);
    }
    return workbook;
  }

  /**
   * Appends a batch of records to the workbook sheet.
   */
  public void appendRows(SXSSFWorkbook workbook, List<PodmiotGospodarczyListDto> records) {
    Sheet sheet = workbook.getSheetAt(0);
    CellStyle cellStyle = createCellStyle(workbook);
    int rowNum = sheet.getLastRowNum() + 1;

    for (PodmiotGospodarczyListDto jdg : records) {
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
      createCell(row, 9, jdg.getAdresKorespondencyjny().map(AdresDto::toString).orElse(Strings.EMPTY), cellStyle);
      createCell(row, 10, jdg.getAdresDzialalnosci().map(AdresDto::toString).orElse(Strings.EMPTY), cellStyle);
    }
  }

  /**
   * Serializes and disposes the streaming workbook, returning its bytes.
   */
  public ByteArrayInputStream finalizeWorkbook(SXSSFWorkbook workbook) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      workbook.write(out);
    } catch (IOException e) {
      log.error("Error writing Excel file: {}", e.getMessage());
      return new ByteArrayInputStream(new byte[0]);
    } finally {
      workbook.dispose(); // delete temp files created by SXSSF
    }
    return new ByteArrayInputStream(out.toByteArray());
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

}
