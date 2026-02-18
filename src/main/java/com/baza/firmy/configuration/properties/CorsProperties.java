package com.baza.firmy.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Konfiguracja CORS dla aplikacji
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "application.cors")
public class CorsProperties {

    /**
     * Lista dozwolonych originów (np. http://localhost:4200)
     */
    private List<String> allowedOrigins;

    /**
     * Lista dozwolonych metod HTTP
     */
    private List<String> allowedMethods;

    /**
     * Lista dozwolonych nagłówków
     */
    private List<String> allowedHeaders;

    /**
     * Lista nagłówków eksponowanych do klienta
     */
    private List<String> exposedHeaders;

    /**
     * Czy dozwolone jest wysyłanie credentials (cookies, authorization headers)
     */
    private boolean allowCredentials;

    /**
     * Maksymalny czas cache dla preflight requests (w sekundach)
     */
    private long maxAge;
}

