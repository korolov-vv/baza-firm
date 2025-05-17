package com.baza.firmy.pkd.domain;

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
@Table (name = "kody_pkd")
class PkdEntity {

  @Id
  @SequenceGenerator (
      name = "kody_pkd_seq",
      allocationSize = 1,
      sequenceName = "kody_pkd_seq")
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "kody_pkd_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  private String kod;
}
