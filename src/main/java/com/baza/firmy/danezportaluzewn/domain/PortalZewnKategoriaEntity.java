package com.baza.firmy.danezportaluzewn.domain;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "portal_zewn_kategorie")
@EntityListeners(AuditingEntityListener.class)
class PortalZewnKategoriaEntity {

  @Id
  @SequenceGenerator (
      name = "portal_zewn_kategorie_seq",
      allocationSize = 1,
      sequenceName = "portal_zewn_kategorie_seq")
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "portal_zewn_kategorie_seq")
  @EqualsAndHashCode.Include
  private Long id;

  private UUID uuid;

  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  private String kategoria;

  private String path;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_pobierania")
  private StatusPobieraniaEnum statusPobierania;

  @Column(columnDefinition = "text")
  private String blad;
}
