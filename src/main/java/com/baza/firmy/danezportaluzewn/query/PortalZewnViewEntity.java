package com.baza.firmy.danezportaluzewn.query;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "portal_zewn_list")
class PortalZewnViewEntity {

  @Id
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private Long id;

  @Column(insertable = false, updatable = false)
  private UUID uuid;

  @Column(name = "create_date", insertable = false, updatable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(name = "create_date", insertable = false, updatable = false)
  private LocalDateTime lastModifiedDate;

  @Builder.Default
  @Column(name = "firmy", columnDefinition = "jsonb", insertable = false, updatable = false)
  private List<FirmaPortalZewnDto> firmy = new ArrayList<>();

  @Column(name = "page_link", insertable = false, updatable = false)
  private String pageLink;

  @Column(name = "next_page_link", insertable = false, updatable = false)
  private String nextPageLink;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_pobierania", insertable = false, updatable = false)
  private StatusPobieraniaEnum statusPobierania;

  @Builder.Default
  @Column(name = "niepobrane_firmy", columnDefinition = "jsonb", insertable = false, updatable = false)
  private List<FirmaPortalZewnDto> niepobraneFirmy = new ArrayList<>();

  @Column(columnDefinition = "text", insertable = false, updatable = false)
  private String blad;
}
