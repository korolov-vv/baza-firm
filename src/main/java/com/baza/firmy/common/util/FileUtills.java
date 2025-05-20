package com.baza.firmy.common.util;


import com.baza.firmy.configuration.s3w.S3DownloadService;
import com.baza.firmy.configuration.s3w.S3UploadService;
import com.baza.firmy.dto.FileDto;
import com.baza.firmy.entity.FileEntity;
import com.baza.firmy.mapper.FileMapper;
import com.baza.firmy.repository.FileRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class FileUtills {

  private final FileRepository fileRepository;
  private final FileMapper fileMapper;
  private final S3DownloadService s3DownloadService;
  private final S3UploadService s3UploadService;

  @Transactional
  public FileDto saveToFile(ByteArrayInputStream excelData, FileDto fileDto) {
    FileEntity file = saveFile(excelData, fileDto);
    s3UploadService.uploadFile(file.getPath(), file.getFileName(), excelData.readAllBytes());
    log.info("File saved to: {}", fileDto.getPath() + fileDto.getFileName());
    return fileMapper.toFileDto(file);
  }

  private FileEntity saveFile(ByteArrayInputStream excelData, FileDto fileDto) {
    FileEntity file;
    if (fileDto.getId() == null) {
      file = new FileEntity();
      file.setUuid(UUID.randomUUID());
      file.setFileName(fileDto.getFileName());
      file.setPath(fileDto.getPath());
      file.setExtention(fileDto.getExtention());
      file.setSize((long) excelData.available());
    } else {
      file = fileRepository.findById(fileDto.getId())
          .orElseThrow(() -> new RuntimeException("File not found"));
    }

    return fileRepository.save(file);
  }

  public void readFromFile(ByteArrayOutputStream out, String filePath, String fileName) {
    byte[] bytes = getFileBytes(filePath, fileName);
    if (bytes.length > 0) {
      try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
          out.write(buffer, 0, bytesRead);
        }
      } catch (Exception e) {
        log.error("Błąd podczas eksportu do pliku xlsx: {}", e.getMessage());
      }
    }
  }


  public File getFile(final String filePath, final String fileName) {
    return s3DownloadService.getFileFromBucket(filePath, fileName);
  }


  private byte[] getFileBytes(final String filePath, final String fileName) {
    return s3DownloadService.getFileBytesFromBucket(filePath, fileName);
  }
}
