package com.baza.firmy.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Klasa jest Utilsem (klasą pomocniczą wykorzystywaną w całym systemie) związaną z formatowaniem dat. Powstała w celu wydzielenia
 * wspólnej logiki.
 */
public class DateUtil {

  public static final String DAY_MONTH_YEAR_DATE = "dd-MM-yyyy";
  public static final String DATE = "yyyy-MM-dd";
  public static final String DAYMONTHYEAR = "ddMMyyyy";
  public static final String YEAR_MONTH = "yyyy-MM-dd";
  public static final String DATETIMESEC = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String DATETIMESECZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  public static final String DATETIMESEC_VIEW = "yyyy-MM-dd HH:mm:ss";
  public static final String DATETIMESEC_VIEW_DO_NAZWY_PLIKU = "yyyy-MM-dd HH-mm-ss";
  public static final String DATETIME = "yyyy-MM-dd HH:mm";
  public static final String YEAR = "yyyy";

  public static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(9999, 12, 31, 12, 0, 0);
  public static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);
  public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);

  public static final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0, 0);

  public static LocalDateTime convertStringToLocalDateTime(String dateTimeValue) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIMESEC);
    return LocalDateTime.parse(dateTimeValue, formatter);
  }

  public static LocalDateTime convertStringToLocalDateTimeWithZone(String dateTimeValue) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIMESECZONE);
    return LocalDateTime.parse(dateTimeValue, formatter);
  }

  /**
   * metoda konwertująca datę w formacie napisu na datę zrozumialą przez system
   *
   * @param dateValue napis zawierający datę
   * @return
   */
  public static LocalDate convertStringToLocalDate(String dateValue) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);
    return LocalDate.parse(dateValue, formatter);
  }

  /**
   * metoda konwertująca datę w formacie napisu na datę zrozumialą przez system
   *
   * @param dateValue napis zawierający datę
   * @return
   */
  public static LocalDate convertStringToDayMonthYear(String dateValue) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_DATE);
    return LocalDate.parse(dateValue, formatter);
  }

  /**
   * metoda konwertująca datę do formatu "yyyy-MM-dd"
   *
   * @param date data
   * @return
   */
  public static String convertDateToString(LocalDate date) {
    if (date == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern((DATE));
    return date.format(formatter);
  }

  /**
   * metoda konwertująca datę do formatu "ddMMyyyy"
   *
   * @param date data
   * @return
   */
  public static String convertDateToStringDayMonthYear(LocalDate date) {
    if (date == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DAYMONTHYEAR);
    return date.format(formatter);
  }

  /**
   * metoda konwertująca datę do formatu "yyyy-MM-dd"
   *
   * @param date data
   * @return
   */
  public static String convertDateToYearMonthString(LocalDate date) {
    if (date == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern((YEAR_MONTH));
    return date.format(formatter);
  }

  /**
   * metoda konwertująca datę do formatu "yyyy-MM-dd HH:mm:ss"
   *
   * @param date data
   * @return
   */
  public static String convertDateToString(LocalDateTime date) {
    if (date == null) {
      return "";
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.DATETIMESEC_VIEW);
    return date.format(formatter);
  }

  /**
   * metoda pobierająca rok z daty
   *
   * @param date data
   * @return
   */
  public static String getYear(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtil.YEAR);
    return date.format(formatter);
  }

  /**
   * metoda parsuje stringa na integer, wyciąga rok
   *
   * @param value - data jako string
   * @return - rok
   */
  public static Integer parseYearFromStringValue(String value) {
    // Parsowanie samego roku
    if (value.matches("\\d{4}")) {
      return Integer.parseInt(value);
    }
    // Jeśli wartość jest pełną datą
    return LocalDate.parse(value).getYear();
  }

  /**
   * metoda pobierająca czas w strefie Europe/Warsaw
   *
   * @return
   */
  public static LocalDateTime getWarsawTime() {
    return LocalDateTime.now(ZoneId.of("Europe/Warsaw"));
  }


  public static boolean czyAktywny(LocalDateTime aktywnyOd, Optional<LocalDateTime> aktywnyDo) {
    return aktywnyOd.isBefore(LocalDateTime.now()) && (aktywnyDo.isEmpty() || aktywnyDo.get().isAfter(LocalDateTime.now()));
  }

  /**
   * Metoda sprawdza czy localDate mieści się w podanym zakresie
   *
   * @param localDate
   * @param dataOd
   * @param dataDo
   * @return
   */
  public static boolean isDateInRange(LocalDate localDate, LocalDate dataOd, LocalDate dataDo) {
    return (localDate != null) &&
        (localDate.isEqual(dataOd) || localDate.isAfter(dataOd)) &&
        (localDate.isEqual(dataDo) || localDate.isBefore(dataDo));
  }

  /**
   * Zwraca czas końca dnia
   *
   * @return
   */
  public static LocalTime endOfDay() {
    return LocalTime.of(23, 59, 59);
  }

  public static String formatujDateDdMmYyyy(LocalDate dataDoSformatowania) {
    DateTimeFormatter dateSofFormater = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_DATE);
    return dataDoSformatowania.format(dateSofFormater);
  }

  /**
   * Liczy dni pomiędzy dwoma datami
   * @param startDate
   * @param endDate
   * @return
   */
  public static Integer countDaysBetween(LocalDate startDate, LocalDate endDate) {
    return Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
  }

  /**
   * Liczy dni pomiędzy dwoma datami
   * @param startDate
   * @param endDate
   * @return
   */
  public static Integer countDaysBetween(LocalDateTime startDate, LocalDateTime endDate) {
    return Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
  }

}
