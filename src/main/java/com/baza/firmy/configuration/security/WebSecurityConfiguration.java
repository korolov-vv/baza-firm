package com.baza.firmy.configuration.security;

import com.baza.firmy.dto.UserPrincipal;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.cors.CorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@EnableConfigurationProperties(AuthorizationDisabledEndpoints.class)
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  private final CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
        .anyRequest().authenticated());
    http.oauth2ResourceServer(oauth2 ->
        oauth2.jwt(jwt ->
            jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())
        ));
    http.cors(cors -> cors.configurationSource(corsConfigurationSource));
    return http.csrf(AbstractHttpConfigurer::disable).build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer(
      AuthorizationDisabledEndpoints authorizationDisabledEndpoints
  ) {
    return web -> web.ignoring()
        .requestMatchers(HttpMethod.GET, authorizationDisabledEndpoints.get())
            .requestMatchers(HttpMethod.POST, authorizationDisabledEndpoints.post());
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
