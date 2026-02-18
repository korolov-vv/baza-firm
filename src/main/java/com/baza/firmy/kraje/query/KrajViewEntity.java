package com.baza.firmy.kraje.query;

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
@Table (name = "kraje")
public class KrajViewEntity {

  @Id
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private Long id;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @Column(insertable = false, updatable = false)
  private String symbol;
  @Column(insertable = false, updatable = false)
  private String kraj;
}
