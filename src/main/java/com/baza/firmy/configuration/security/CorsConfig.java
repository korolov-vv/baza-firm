package com.baza.firmy.configuration.security;

import com.baza.firmy.configuration.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Konfiguracja CORS (Cross-Origin Resource Sharing) dla aplikacji
 * Umożliwia requesty z frontend aplikacji Angular
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final CorsProperties corsProperties;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Configuring CORS with allowed origins: {}", corsProperties.getAllowedOrigins());

        CorsConfiguration configuration = new CorsConfiguration();

        // Dozwolone originy (frontendowe aplikacje)
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());

        // Dozwolone metody HTTP
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());

        // Dozwolone nagłówki
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());

        // Nagłówki eksponowane do klienta
        configuration.setExposedHeaders(corsProperties.getExposedHeaders());

        // Credentials (cookies, authorization headers)
        configuration.setAllowCredentials(corsProperties.isAllowCredentials());

        // Max age dla preflight cache
        configuration.setMaxAge(corsProperties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Zastosuj konfigurację CORS dla wszystkich endpointów
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

