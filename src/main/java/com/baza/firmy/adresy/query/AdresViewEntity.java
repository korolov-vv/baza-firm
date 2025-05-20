package com.baza.firmy.adresy.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class AdresViewEntity {

  @Id
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private Long id;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String kraj;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String wojewodztwo;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String powiat;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String gmina;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String miasto;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String ulica;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String budynek;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String lokal;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String kodPocztowy;
  @Column(insertable = false, updatable = false)
  private String terc;
  @Column(insertable = false, updatable = false)
  private String simc;
  @Column(insertable = false, updatable = false)
  private String ulic;
}
