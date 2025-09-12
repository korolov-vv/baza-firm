package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.adresy.query.AdresViewEntity;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.osoby.domain.dto.ReprezentacjaDto;
import com.baza.firmy.osoby.domain.dto.WlascicielDto;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.query.PkdViewEntity;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.SpolkaDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "podmioty_gospodarcze")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
class PodmiotGospodarczyEntity {

    @Id
    @SequenceGenerator(
            name = "podmioty_gospodarcze_seq",
            allocationSize = 1,
            sequenceName = "podmioty_gospodarcze_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "podmioty_gospodarcze_seq")
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
    @Version
    private int version;

    private String nazwa;

    @Enumerated(EnumType.STRING)
    private Rejestr rejestr;
    private UUID ceidgId;
    private String nip;
    private String regon;
    private String numerKrs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adres_dzialalnosci_id", referencedColumnName = "id")
    private AdresViewEntity adresDzialalnosci;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adres_korespondencyjny_id", referencedColumnName = "id")
    private AdresViewEntity adresKorespondencyjny;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wlasciciel_id", referencedColumnName = "id")
    private OsobaViewEntity wlasciciel;

    @Builder.Default
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<WlascicielDto> wspolnicySpzoo = new ArrayList<>();

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private ReprezentacjaDto reprezentacja;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pkd_glowny_id", referencedColumnName = "id")
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
