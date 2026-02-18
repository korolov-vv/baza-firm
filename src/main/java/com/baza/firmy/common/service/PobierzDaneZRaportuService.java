package com.baza.firmy.common.service;

import com.baza.firmy.constants.enums.WojewodztwaRaportyEnum;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PobierzDaneZRaportuService {

  private final PobierzDaneZRaportuExecutor pobierzDaneZRaportuExecutor;

  public void pobierzDaneZRaportu() {
    log.info("Zaczynam pobieranie danych z raportu");

    Arrays.stream(WojewodztwaRaportyEnum.values())
        .forEach(pobierzDaneZRaportuExecutor::zapiszDaneDlaWojewodztwa);
    log.info("Zakończono pobieranie danych z raportu");
  }
}
