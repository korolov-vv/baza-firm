package com.baza.firmy.configuration.smtp;

import com.baza.firmy.configuration.properties.SmtpProperties;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@RequiredArgsConstructor
public class SmtpConfig {

  private final SmtpProperties smtpProperties;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(smtpProperties.getHost());
    mailSender.setPort(Integer.parseInt(smtpProperties.getPort()));

    mailSender.setUsername(smtpProperties.getUsername());
    mailSender.setPassword(smtpProperties.getPassword());

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }
}
