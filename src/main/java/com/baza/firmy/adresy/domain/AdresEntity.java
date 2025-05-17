package com.baza.firmy.adresy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table (name = "adresy")
class AdresEntity {

  @Id
  @SequenceGenerator (
      name = "adresy_seq",
      allocationSize = 1,
      sequenceName = "adresy_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresy_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @EqualsAndHashCode.Include
  private String kraj;
  @EqualsAndHashCode.Include
  private String wojewodztwo;
  @EqualsAndHashCode.Include
  private String powiat;
  @EqualsAndHashCode.Include
  private String gmina;
  @EqualsAndHashCode.Include
  private String miasto;
  @EqualsAndHashCode.Include
  private String ulica;
  @EqualsAndHashCode.Include
  private String budynek;
  @EqualsAndHashCode.Include
  private String lokal;
  @EqualsAndHashCode.Include
  private String kodPocztowy;
  private String terc;
  private String simc;
  private String ulic;
}
