package com.baza.firmy.danezportaluzewn.domain;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "portal_zewn_list")
@EntityListeners (AuditingEntityListener.class)
class PortalZewnEntity {

  @Id
  @SequenceGenerator (
      name = "portal_zewn_list_seq",
      allocationSize = 1,
      sequenceName = "portal_zewn_list_seq")
  @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "portal_zewn_list_seq")
  @EqualsAndHashCode.Include
  private Long id;

  private UUID uuid;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  @Builder.Default
  @Column(name = "firmy", columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<FirmaPortalZewnDto> firmy = new ArrayList<>();

  @Column(name = "page_link")
  private String pageLink;

  @Column(name = "next_page_link")
  private String nextPageLink;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_pobierania")
  private StatusPobieraniaEnum statusPobierania;

  @Builder.Default
  @Column(name = "niepobrane_firmy", columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<FirmaPortalZewnDto> niepobraneFirmy = new ArrayList<>();
}
