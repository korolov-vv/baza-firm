package com.baza.firmy.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import net.kaczmarzyk.spring.data.jpa.swagger.springdoc.SpecificationArgResolverSpringdocOperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Baza firm API")
            .version("0.1.0")
            .description("API dla dostępu do Bazy firm RP")
            .contact(new Contact()
                .url("http://localhost:8080")));
  }

  @Bean
  public SpecificationArgResolverSpringdocOperationCustomizer specificationArgResolverSpringdocOperationCustomizer() {
    return new SpecificationArgResolverSpringdocOperationCustomizer();
  }
}
