package com.baza.firmy.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtills {

  public void saveToFile(ByteArrayInputStream excelData, String fileName) {
    File reportsDir = returnCatalog("schrack");

    File file = new File(reportsDir, fileName);
    try (FileOutputStream fos = new FileOutputStream(file)) {
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = excelData.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
      }
      log.info("File saved to: {}", file.getAbsolutePath());
    } catch (IOException e) {
      log.error("Error saving file: {}", e.getMessage());
    }
  }

  public void readFromFile(ByteArrayOutputStream out, String fileName) {
    File file = new File("schrack", fileName);
    if (file.exists()) {
      try (FileInputStream fis = new FileInputStream(file)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
          out.write(buffer, 0, bytesRead);
        }
      } catch (Exception e) {
        log.error("Błąd podczas eksportu do pliku xlsx: {}", e.getMessage());
      }
    }
  }

  private File returnCatalog(String nazwaKatalogu) {
    File katalog = new File(nazwaKatalogu);
    if (!katalog.exists()) {
      katalog.mkdirs();
    }
    return katalog;
  }
}
