package com.baza.firmy.subscrypcje.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "subscrypcje")
class SubscrypcjaViewEntity {

  @Id
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @Column(insertable = false, updatable = false)
  private String nazwa;
  @Column(insertable = false, updatable = false)
  private String opis;
  @Column(insertable = false, updatable = false)
  private int iloscDostepnychFirm;
  @Column(name = "okres_trwania_subskrypcji_w_dniach", insertable = false, updatable = false)
  private int okresTrwaniaWDniach;
}
