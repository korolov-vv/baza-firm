package com.baza.firmy.dto;

import com.baza.firmy.podmiotygospodarcze.domain.dto.Pkd;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@XmlRootElement(name = "Detail")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Detail")
public class JdgSzczegolyRaportDto implements JdgSzczegoly, Serializable {

  @XmlElement(name = "Lp.")
  private String lp;
  @XmlElement(name = "Nip")
  private String nip;
  @XmlElement(name = "Regon")
  private String regon;
  @XmlElement(name = "NazwaPodmiotu")
  private String nazwaPodmiotu;
  @XmlElement(name = "Nazwisko")
  private String nazwisko;
  @XmlElement(name = "Imie")
  private String imie;
  @XmlElement(name = "Telefon")
  private String telefon;
  @XmlElement(name = "Email")
  private String email;
  @XmlElement(name = "AdresWWW")
  private String adresWWW;
  @XmlElement(name = "KodPocztowy")
  private String kodPocztowy;
  @XmlElement(name = "Powiat")
  private String powiat;
  @XmlElement(name = "Gmina")
  private String gmina;
  @XmlElement(name = "Miejscowosc")
  private String miejscowosc;
  @XmlElement(name = "Ulica")
  private String ulica;
  @XmlElement(name = "NrBudynku")
  private String nrBudynku;
  @XmlElement(name = "NrLokalu")
  private String nrLokalu;
  @XmlElement(name = "glownyKodPkd")
  private String glownyKodPkd;
  @XmlElement(name = "PozostaleKodyPkd")
  private String pozostaleKodyPkd;
  @XmlElement(name = "RokPKD")
  private String rokPkd;
  @XmlElement(name = "StatusDzialalnosci")
  private String statusDzialalnosci;
  @XmlElement(name = "DataRozpoczeciaDzialalnosci")
  private String dataRozpoczeciaDzialalnosci;

  @Override
  public Optional<Pkd> getPkdGlowny() {
    return Optional.ofNullable(glownyKodPkd)
            .map(buildPkd());
  }

  @Override
  public List<Pkd> getPkd() {
      if (pozostaleKodyPkd == null) {
          return Collections.emptyList();
      }
      return Arrays.stream(pozostaleKodyPkd.split("\\$##\\$"))
              .map(buildPkd())
              .toList();
  }

  public Optional<String> getNip() {
    return Optional.ofNullable(nip);
  }

  public Optional<String> getRegon() {
    return Optional.ofNullable(regon);
  }

  public Optional<String> getNazwaPodmiotu() {
    return Optional.ofNullable(nazwaPodmiotu);
  }

  public Optional<String> getNazwisko() {
    return Optional.ofNullable(nazwisko);
  }

  public Optional<String> getImie() {
    return Optional.ofNullable(imie);
  }

  public Optional<String> getTelefon() {
    return Optional.ofNullable(telefon);
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(email);
  }

  public Optional<String> getAdresWWW() {
    return Optional.ofNullable(adresWWW);
  }

  public Optional<String> getKodPocztowy() {
    return Optional.ofNullable(kodPocztowy);
  }

  public Optional<String> getPowiat() {
    return Optional.ofNullable(powiat);
  }

  public Optional<String> getGmina() {
    return Optional.ofNullable(gmina);
  }

  public Optional<String> getMiejscowosc() {
    return Optional.ofNullable(miejscowosc);
  }

  public Optional<String> getUlica() {
    return Optional.ofNullable(ulica);
  }

  public Optional<String> getNrBudynku() {
    return Optional.ofNullable(nrBudynku);
  }

  public Optional<String> getNrLokalu() {
    return Optional.ofNullable(nrLokalu);
  }

  public Optional<String> getRokPkd() {
    return Optional.ofNullable(rokPkd);
  }

  public Optional<String> getDataRozpoczeciaDzialalnosci() {
    return Optional.ofNullable(dataRozpoczeciaDzialalnosci);
  }

  private static Function<String, Pkd> buildPkd() {
      return pkd -> Pkd.builder()
              .kod(pkd)
              .nazwa("")
              .build();
  }
}
