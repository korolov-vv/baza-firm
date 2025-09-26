package com.baza.firmy.danezportaluzewn.query;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "portal_zewn_kategorie")
class PortalZewnKategoriaViewEntity {

  @Id
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private Long id;

  @Column(insertable = false, updatable = false)
  private UUID uuid;

  @Column(insertable = false, updatable = false)
  private String kategoria;

  @Column(insertable = false, updatable = false)
  private String path;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_pobierania", insertable = false, updatable = false)
  private StatusPobieraniaEnum statusPobierania;

  @Column(columnDefinition = "text", insertable = false, updatable = false)
  private String blad;
}
