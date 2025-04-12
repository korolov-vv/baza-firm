package com.baza.firmy.configuration.s3w;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * Klasa jest Serwisem (element pośredniczący) związaną z usuwaniem plików. Powstała w celu wydzielenia wspólnej logiki pomiędzy
 * Use Case'ami dotyczących usuwania plików z serwera plików.
 */
@Slf4j
@RequiredArgsConstructor
public class S3DeleteService {

  private final S3Client s3Client;

  void deleteFile(final String filePath, final String fileName) {
    String fileKey = filePath + "/" + fileName;
    try {
      s3Client.deleteObject(request ->
          request
              .bucket(S3Constants.BUCKET_NAME)
              .key(fileKey));
    } catch (S3Exception e) {
      throw S3Exceptions.nieMoznaUsunacPliku(fileName, e);
    }
  }
}
