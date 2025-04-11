package com.baza.firmy.configuration.s3w;

import com.baza.firmy.configuration.properties.AwsProperties;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
class S3AwsConfiguration {

  private final AwsProperties awsProperties;

  @Bean
  public S3Client s3Client() {
    AwsCredentials credentials = AwsBasicCredentials.create(awsProperties.getAccessKey(), awsProperties.getSecretKey());

    return S3Client
        .builder()
        .region(Region.of(Region.US_EAST_1.toString()))
        .endpointOverride(URI.create(awsProperties.getEndpoint()))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
  }
}
