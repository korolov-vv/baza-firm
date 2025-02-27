package com.baza.firmy.entity;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.response.SpolkaDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "jednoosobowe_dzialalnosci_gospodarcze")
public class JednoosobowaDzialalnoscGospodarcza {

  @Id
  @SequenceGenerator (
      name = "jednoosobowe_dzialalnosci_gospodarcze_seq",
      allocationSize = 1,
      sequenceName = "jednoosobowe_dzialalnosci_gospodarcze_seq")
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "jednoosobowe_dzialalnosci_gospodarcze_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @EqualsAndHashCode.Include
  private String nazwa;
  @EqualsAndHashCode.Include
  private UUID ceidgId;

  @OneToOne
  @JoinColumn (name = "adres_dzialalnosci_id", referencedColumnName = "id")
  private Adres adresDzialalnosci;

  @OneToOne
  @JoinColumn (name = "adres_korespondencyjny_id", referencedColumnName = "id")
  private Adres adresKorespondencyjny;

  @OneToOne
  @JoinColumn(name = "wlasciciel_id", referencedColumnName = "id")
  private Osoba wlasciciel;

  private String pkdGlowny;

  @OneToMany
  @JoinTable(
      name = "firmy_pkd",
      joinColumns = @JoinColumn(name = "firma_id"),
      inverseJoinColumns = @JoinColumn(name = "pkd_id"))
  @Builder.Default
  private List<Pkd> pkd = new ArrayList<>();

  @Builder.Default
  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<SpolkaDto> spolki = new ArrayList<>();

  private String dataRozpoczecia;
  private String dataZawieszenia;
  private String dataZakonczenia;
  private String dataWykreslenia;
  private String dataWznowienia;

  @Enumerated(EnumType.STRING)
  private BusinessStatus status;
  private int numerStatusu;
  private String telefon;
  private String email;
  private String www;
  private String adresDoreczenElektronicznych;
  private String innaFormaKonaktu;

  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private String pelneInfo;
  
  private String link;
}
