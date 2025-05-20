package com.baza.firmy.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WojewodztwaRaportyEnum {

  DOLNOSLASKIE("Zarejestrowane dzialalnosci - wojewodztwo dolnoslaskie.xml"),
  KUJAWSKO_POMORSKIE("Zarejestrowane dzialalnosci - wojewodztwo kujawsko-pomorskie.xml"),
  LUBELSKIE("Zarejestrowane dzialalnosci - wojewodztwo lubelskie.xml"),
  LUBUSKIE("Zarejestrowane dzialalnosci - wojewodztwo lubuskie.xml"),
  LODZKIE("Zarejestrowane dzialalnosci - wojewodztwo lodzkie.xml"),
  MALOPOLSKIE("Zarejestrowane dzialalnosci - wojewodztwo malopolskie.xml"),
  MAZOWIECKIE("Zarejestrowane dzialalnosci - wojewodztwo mazowieckie.xml"),
  OPOLSKIE("Zarejestrowane dzialalnosci - wojewodztwo opolskie.xml"),
  PODKARPACKIE("Zarejestrowane dzialalnosci - wojewodztwo podkarpatskie.xml"),
  PODLASKIE("Zarejestrowane dzialalnosci - wojewodztwo podlaskie.xml"),
  POMORSKIE("Zarejestrowane dzialalnosci - wojewodztwo pomorskie.xml"),
  SLASKIE("Zarejestrowane dzialalnosci - wojewodztwo slaskie.xml"),
  SWIETOKRZYSKIE("Zarejestrowane dzialalnosci - wojewodztwo swietokrzyskie.xml"),
  WARMINSKO_MAZURSKIE("Zarejestrowane dzialalnosci - wojewodztwo warminsko-mazurskie.xml"),
  WIELKOPOLSKIE("Zarejestrowane dzialalnosci - wojewodztwo wielkopolskie.xml"),
  ZACHODNIOPOMORSKIE("Zarejestrowane dzialalnosci - wojewodztwo zahodniopomorskie.xml"),
  BRAK_WOJEWODZTWA("Zarejestrowane dzialalnosci - brak wojewodztwa.xml");

  private final String nazwaPlikuRaportu;
}
