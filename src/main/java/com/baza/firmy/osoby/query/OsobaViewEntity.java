package com.baza.firmy.osoby.query;

import com.baza.firmy.kraje.query.KrajViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "osoby")
public class OsobaViewEntity {

  @Id
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private Long id;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String pesel;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String nip;
  @Column(insertable = false, updatable = false)
  private String regon;
  @Column(insertable = false, updatable = false)
  private String imie;
  @Column(insertable = false, updatable = false)
  private String nazwisko;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable (name = "obywatelstwa",
      joinColumns = @JoinColumn(name = "osoba_id"),
      inverseJoinColumns = @JoinColumn (name = "kraj_id"))
  @Builder.Default
  private List<KrajViewEntity> obywatelstwa = new ArrayList<>();
}
