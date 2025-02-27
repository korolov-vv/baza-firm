package com.baza.firmy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
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
import org.hibernate.envers.NotAudited;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "osoby")
public class Osoba {

  @Id
  @SequenceGenerator (
      name = "osoby_seq",
      allocationSize = 1,
      sequenceName = "osoby_seq")
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "osoby_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @EqualsAndHashCode.Include
  private String pesel;
  @EqualsAndHashCode.Include
  private String nip;
  private String regon;
  private String imie;
  private String nazwisko;
  @NotAudited
  @OneToMany
  @JoinTable (name = "obywatelstwa",
      joinColumns = @JoinColumn(name = "osoba_id"),
      inverseJoinColumns = @JoinColumn (name = "kraj_id"))
  @Builder.Default
  private List<Kraj> obywatelstwa = new ArrayList<>();
}
