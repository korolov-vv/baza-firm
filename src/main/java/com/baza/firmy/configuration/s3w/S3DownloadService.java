package com.baza.firmy.configuration.s3w;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest.Builder;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

/**
 * Klasa jest Serwisem (element pośredniczący) związaną z pobieraniem plików. Powstała w celu wydzielenia wspólnej logiki pomiędzy
 * Use Case'ami dotyczących pobierania plików lub spakowanych plików z serwera plików.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class S3DownloadService {

  private final S3Client s3Client;

  @Async
  void downloadFile(final String filePath, final String fileName, HttpServletResponse response) {
    ByteArrayInputStream bis = new ByteArrayInputStream(getFileFromBucket(filePath, fileName));

    try {
      String contentDisposition = "attachment;filename*=" + encodeFileName(fileName);

      response.addHeader("Content-disposition", contentDisposition);
      response.setContentType(URLConnection.guessContentTypeFromName(fileName));

      IOUtils.copy(bis, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException e) {
      throw S3Exceptions.nieMoznaPobracPliku(fileName, e);
    }

  }

  public byte[] getFileFromBucket(final String filePath, final String fileName) {
    byte[] content = null;
    final ResponseInputStream<GetObjectResponse> stream = s3Client.getObject(
        getObjectRequestBuilder(S3Constants.BUCKET_NAME, filePath, fileName));
    try {
      content = IOUtils.toByteArray(stream);
      stream.close();
    } catch (final IOException ex) {
      log.error("Błąd podczas pobierania pliku " + fileName);
    }
    return content;
  }

  private static String encodeFileName(String fileName) throws UnsupportedEncodingException {
    return URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
  }

  private Consumer<Builder> getObjectRequestBuilder(String bucketName, String filePath, String fileName) {
    return request -> request
        .bucket(bucketName)
        .key(filePath + "/" + fileName);
  }

  private byte[] zwrocDomyslnyPlik(String fileName) {
    String rozszerzenie = FileNameUtils.getExtension(fileName);
    String nazwaPliku = "plik-domyslny." + rozszerzenie;
    ClassLoader classLoader = S3DownloadService.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream("domyslnyplikpobierania/" + nazwaPliku)) {
      if (inputStream == null) {
        return null;
      }
      return inputStream.readAllBytes();
    } catch (final IOException ex) {
      log.error("Błąd podczas pobierania domyślnego pliku");
    }
    return null;
  }
}
