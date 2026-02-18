package com.baza.firmy.configuration.quartz;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Klasa jest konfiguracja (przygotowuje aplikacje do poprawnego działania)
 * Służy do przygotowania mechanizmu zadań cyklicznych
 */
@Slf4j
@Configuration
@EnableAutoConfiguration
@EnableJpaAuditing
public class QuartzConfig {

  @Autowired
  private ApplicationContext applicationContext;

  @PostConstruct
  public void init() {
    log.info("Scheduler włączony");
  }

  /**
   * Metoda przygotowuje mechanizm dodawanie zadań cykicznych
   * @return instancja tworząca zadania cykliczne
   */
  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }


}
