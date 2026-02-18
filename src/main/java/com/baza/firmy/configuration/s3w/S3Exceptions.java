package com.baza.firmy.configuration.s3w;



class S3Exceptions {

  static RuntimeException nieMoznaPobracPliku(String nazwaPliku, Exception e) {
    return new RuntimeException(
        String.format("Błąd podczas pobierania pliku %s. Bład: %s", nazwaPliku, e.getMessage()));
  }

  static RuntimeException nieMoznaDodacPliku(String nazwaPliku, Exception e) {
    return new RuntimeException(
        String.format("Błąd podczas dodawania pliku %s. Bład: %s", nazwaPliku, e.getMessage()));
  }

  static RuntimeException nieMoznaUsunacPliku(String nazwaPliku, Exception e) {
    return new RuntimeException(
        String.format("Błąd podczas usuwania pliku %s. Bład: %s", nazwaPliku, e.getMessage()));
  }

  static RuntimeException bladPodczasTworzeniaKontenera(Exception e) {
    return new RuntimeException("Błąd podczas dodawania nowego kontenera: " + e.getStackTrace());
  }
}
