package com.baza.firmy.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtills {

  public void saveExcelToFile(ByteArrayInputStream excelData, String fileName) {
    File reportsDir = new File("dla_kamila");
    if (!reportsDir.exists()) {
      reportsDir.mkdirs();
    }

    File file = new File(reportsDir, fileName);
    try (FileOutputStream fos = new FileOutputStream(file)) {
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = excelData.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
      }
      log.info("Excel file saved to: {}", file.getAbsolutePath());
    } catch (IOException e) {
      log.error("Error saving Excel file: {}", e.getMessage());
    }
  }
}
