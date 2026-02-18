package com.baza.firmy.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import net.kaczmarzyk.spring.data.jpa.swagger.springdoc.SpecificationArgResolverSpringdocOperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String TITLE = "Baza Firm admin console";
    private static final String DESCRIPTION =
            "Baza Firm admin console";
    private static final String VERSION = "0.0.1-SNAPSHOT";
    private static final String API_KEY_SCHEMA = "ApiKeyAuth";
    private static final String X_API_KEY = "x-api-key";

    @Bean
    public OpenAPI openApi() {
        final var info = new Info().title(TITLE).description(DESCRIPTION).version(VERSION);
        final var apiKeySecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name(X_API_KEY);
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY_SCHEMA, apiKeySecurityScheme))
                .info(info);
    }

  @Bean
  public SpecificationArgResolverSpringdocOperationCustomizer specificationArgResolverSpringdocOperationCustomizer() {
    return new SpecificationArgResolverSpringdocOperationCustomizer();
  }
}
