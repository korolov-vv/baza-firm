package com.baza.firmy.firmycrm.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Table (name = "firmy_crm")
@EntityListeners(AuditingEntityListener.class)
class FirmaCrmEntity {

  @Id
  @SequenceGenerator(
          name = "firmy_crm_seq",
          allocationSize = 1,
          sequenceName = "firmy_crm_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "firmy_crm_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  @EqualsAndHashCode.Include
  @Version
  private int version;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "firma_klient_id", referencedColumnName = "id")
  private PodmiotGospodarczyViewEntity firmaKlient;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "firma_id", referencedColumnName = "id")
  private PodmiotGospodarczyViewEntity firmaCrm;
  @Enumerated(EnumType.STRING)
  private StatusKontaktu statusKontaktu;
  @Enumerated(EnumType.STRING)
  private SposobKontaktu sposobKontaktu;
  private int liczbaProbKontaktu;
  private LocalDateTime dataOstatniegoKontaktu;
  private LocalDateTime dataNastepnegoKontaktu;
  private String komentarz;
}
