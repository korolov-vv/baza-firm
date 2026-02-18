package com.baza.firmy.common.harmonogram.scheduler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa jest bazową strukturą danych dla szczegółów zadań w Quartz.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
abstract class QuartzSzczegoly {

  private String identyfikator;
  private String opis;
}
