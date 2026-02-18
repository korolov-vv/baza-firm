package com.baza.firmy.configuration.security;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.authorization.disabled")
public record AuthorizationDisabledEndpoints(String[] get, String[] post) { }
