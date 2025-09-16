package com.baza.firmy.podmiotygospodarcze.query;

import com.baza.firmy.adresy.query.AdresViewEntity;
import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.osoby.query.OsobaViewEntity;
import com.baza.firmy.pkd.query.PkdViewEntity;
import com.baza.firmy.podmiotygospodarcze.domain.Rejestr;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyArchiveDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.JdgSzczegolyDto;
import com.baza.firmy.podmiotygospodarcze.domain.dto.SpolkaDto;
import com.baza.firmy.response.krs.OdpisAktualnyResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "podmioty_gospodarcze")
public class PodmiotGospodarczeViewEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private Long id;
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private UUID uuid;
    @CreatedDate
    @Column(insertable = false, updatable = false)
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(insertable = false, updatable = false)
    private LocalDateTime lastModifiedDate;
    @Version
    @Column(insertable = false, updatable = false)
    private int version;
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private String nazwa;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private Rejestr rejestr;

    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private UUID ceidgId;
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private String nip;
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private String regon;
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
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

    @Column(insertable = false, updatable = false)
    private String rokPkd;

    @Builder.Default
    @Column(columnDefinition = "jsonb", insertable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<SpolkaDto> spolki = new ArrayList<>();

    @Column(insertable = false, updatable = false)
    private LocalDate dataRozpoczecia;
    @Column(insertable = false, updatable = false)
    private LocalDate dataZawieszenia;
    @Column(insertable = false, updatable = false)
    private LocalDate dataZakonczenia;
    @Column(insertable = false, updatable = false)
    private LocalDate dataWykreslenia;
    @Column(insertable = false, updatable = false)
    private LocalDate dataWznowienia;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private BusinessStatus status;
    @Column(insertable = false, updatable = false)
    private int numerStatusu;
    @Column(insertable = false, updatable = false)
    private String telefon;
    @Column(insertable = false, updatable = false)
    private String email;
    @Column(insertable = false, updatable = false)
    private String www;
    @Column(insertable = false, updatable = false)
    private String adresDoreczenElektronicznych;
    @Column(insertable = false, updatable = false)
    private String innaFormaKontaktu;

    @Column(columnDefinition = "jsonb", insertable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private JdgSzczegolyArchiveDto pelneInfoArchive;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JdgSzczegolyDto pelneInfo;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private OdpisAktualnyResponse pelneInfoSpolka;

    @Column(insertable = false, updatable = false)
    private String link;

    public Optional<UUID> getCeidgId() {
        return Optional.ofNullable(ceidgId);
    }

    public Optional<OsobaViewEntity> getWlasciciel() {
        return Optional.ofNullable(wlasciciel);
    }

    public Optional<AdresViewEntity> getAdresDzialalnosci() {
        return Optional.ofNullable(adresDzialalnosci);
    }

    public Optional<PkdViewEntity> getPkdEntityGlowny() {
        return Optional.ofNullable(pkdGlowny);
    }

    public Optional<PkdViewEntity> getPkdGlowny() {
        return Optional.ofNullable(pkdGlowny);
    }

    public List<PkdViewEntity> getPkd() {
        if (pkd == null) {
            return Collections.emptyList();
        } else {
            return pkd;
        }
    }

    public Optional<String> getNumerKrs() {
        return Optional.ofNullable(numerKrs);
    }
}
