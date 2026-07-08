package com.baza.firmy.configuration.s3w;


import com.baza.firmy.configuration.properties.AwsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import static com.baza.firmy.configuration.s3w.S3Exceptions.nieMoznaDodacPliku;

/**
 * Klasa jest Serwisem (element pośredniczący) związaną z dodawaniem plików. Powstała w celu wydzielenia wspólnej logiki pomiędzy
 * Use Case'ami dotyczących dodawania plików do serwera plików.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

  private final S3Client s3Client;
  private final AwsProperties awsProperties;

  @Async
  void uploadFile(final String filePath, final MultipartFile multipartFile) {
    try {
      final File file = convertMultiPartFileToFile(multipartFile);
      uploadFileToS3Bucket(filePath, file);
      boolean ignore = file.delete(); // usunięcie pliku który tworzy sie na lokalnym dysku
    } catch (final S3Exception ex) {
      throw nieMoznaDodacPliku(multipartFile.getOriginalFilename(), ex);
    }
  }


  @Async
  public void uploadFile(final String filePath, final String filename, final byte[] fileBytes) {
    try {
      final File file = writeByte(fileBytes, filename);
      uploadFileToS3Bucket(filePath, file);
      boolean ignore = file.delete(); // usunięcie pliku który tworzy sie na lokalnym dysku
    } catch (final S3Exception ex) {
      log.error("Błąd podczas dodawania pliku", ex);
      throw nieMoznaDodacPliku(filename, ex);
    }
  }

  private File writeByte(final byte[] bytes, final String filename) {
    File tempDir = new File(awsProperties.getTempDirPath() + UUID.randomUUID());
    tempDir.mkdirs();
    File file = new File(tempDir.getAbsolutePath() + '/' + filename);
    try(OutputStream os = new FileOutputStream(file)) {
      os.write(bytes);
      return file;
    } catch (Exception e) {
      log.error("Błąd podczas dodawania pliku", e);
      throw nieMoznaDodacPliku(filename, e);
    }
  }

  private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
    final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    try (final FileOutputStream outputStream = new FileOutputStream(file)) {
      outputStream.write(multipartFile.getBytes());
    } catch (final IOException ex) {
      log.error("Błąd konwersji z multipart na plik: " + ex.getMessage());
    }
    return file;
  }

  private void uploadFileToS3Bucket(final String filePath, final File file) {
//    if (!bucketExists(S3Constants.BUCKET_NAME)) {
//      createNewBucket();
//    }

    s3Client.putObject(putObjectRequest(S3Constants.BUCKET_NAME, filePath, file), RequestBody.fromFile(file));
  }

  private PutObjectRequest putObjectRequest(String bucketName, final String filePath, final File file) {
    return PutObjectRequest.builder()
        .checksumSHA256(file.getName())
        .bucket(bucketName)
        .key(filePath + "/" + file.getName())
        .build();
  }

  private boolean bucketExists(String bucketName) {
    try {
      s3Client.headBucket(headBucketRequest(bucketName));
      return true;
    }
    catch (NoSuchBucketException exception) {
      return false;
    }
  }

  private Consumer<HeadBucketRequest.Builder> headBucketRequest(String bucketName) {
    return request -> request.bucket(bucketName);
  }

  private void createNewBucket() {
    try {
      s3Client.createBucket(createBucketRequest(S3Constants.BUCKET_NAME));
      log.info("Bucket " + S3Constants.BUCKET_NAME + " create!");
    } catch (S3Exception e) {
      throw S3Exceptions.bladPodczasTworzeniaKontenera(e);
    }
  }

  private Consumer<CreateBucketRequest.Builder> createBucketRequest(String bucketName) {
    return request -> request.bucket(bucketName);
  }

}
