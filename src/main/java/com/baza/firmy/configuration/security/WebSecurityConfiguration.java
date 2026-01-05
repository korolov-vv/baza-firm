package com.baza.firmy.configuration.security;

import com.baza.firmy.dto.UserPrincipal;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@EnableConfigurationProperties(AuthorizationDisabledEndpoints.class)
public class WebSecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
    http.authorizeHttpRequests(authorize -> authorize
        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
        .anyRequest().authenticated());
    http.oauth2ResourceServer(oauth2 ->
        oauth2.jwt(jwt ->
            jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())
        ));
    return http.csrf(AbstractHttpConfigurer::disable).build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(
      AuthorizationDisabledEndpoints authorizationDisabledEndpoints
  ) {
    return web -> web.ignoring()
        .requestMatchers(HttpMethod.GET, authorizationDisabledEndpoints.get());
  }

  @Bean
  public CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
    return new CustomJwtAuthenticationConverter();
  }

  private static class CustomJwtAuthenticationConverter
      implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
      final var roles = jwt.getClaimAsStringList("roles");
      final var principal = new UserPrincipal(
          jwt.getSubject(),
          roles);
      return new UsernamePasswordAuthenticationToken(principal, jwt,
          roles.stream()
          .map(SimpleGrantedAuthority::new)
          .toList());
    }

  }

}
