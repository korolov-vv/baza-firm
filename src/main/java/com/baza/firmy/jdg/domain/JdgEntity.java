package com.baza.firmy.jdg.domain;

import com.baza.firmy.adresy.query.AdresViewEntity;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.jdg.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.jdg.domain.dto.SpolkaDto;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.query.PkdViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import jakarta.persistence.Version;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "jednoosobowe_dzialalnosci_gospodarcze")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
class JdgEntity {

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
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  @EqualsAndHashCode.Include
  private String nazwa;
  private UUID ceidgId;
  @Version
  private int version;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn (name = "adres_dzialalnosci_id", referencedColumnName = "id")
  private AdresViewEntity adresDzialalnosci;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn (name = "adres_korespondencyjny_id", referencedColumnName = "id")
  private AdresViewEntity adresKorespondencyjny;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "wlasciciel_id", referencedColumnName = "id")
  private OsobaViewEntity wlasciciel;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn (name = "pkd_glowny_id", referencedColumnName = "id")
  private PkdViewEntity pkdGlowny;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "firmy_pkd",
      joinColumns = @JoinColumn(name = "firma_id"),
      inverseJoinColumns = @JoinColumn(name = "pkd_id"))
  @Builder.Default
  private List<PkdViewEntity> pkd = new ArrayList<>();

  private String rokPkd;

  @Builder.Default
  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<SpolkaDto> spolki = new ArrayList<>();

  private LocalDate dataRozpoczecia;
  private LocalDate dataZawieszenia;
  private LocalDate dataZakonczenia;
  private LocalDate dataWykreslenia;
  private LocalDate dataWznowienia;

  @Enumerated(EnumType.STRING)
  private BusinessStatus status;
  private int numerStatusu;
  private String telefon;
  private String email;
  private String www;
  private String adresDoreczenElektronicznych;
  private String innaFormaKontaktu;

  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private JdgSzczegolyDto pelneInfo;
  
  private String link;

  public Optional<UUID> getCeidgId() {
    return Optional.ofNullable(ceidgId);
  }

  public Optional<OsobaViewEntity> getWlasciciel() {
    return Optional.ofNullable(wlasciciel);
  }

  public Optional<AdresViewEntity> getAdresEntityDzialalnosci() {
    return Optional.ofNullable(adresDzialalnosci);
  }

  public Optional<PkdViewEntity> getPkdEntityGlowny() {
    return Optional.ofNullable(pkdGlowny);
  }
}
