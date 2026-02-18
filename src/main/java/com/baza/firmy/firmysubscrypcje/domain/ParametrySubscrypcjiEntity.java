package com.baza.firmy.firmysubscrypcje.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "parametry_subscrypcji")
@EntityListeners(AuditingEntityListener.class)
class ParametrySubscrypcjiEntity {

  @Id
  @SequenceGenerator(
          name = "parametry_subscrypcji_seq",
          allocationSize = 1,
          sequenceName = "parametry_subscrypcji_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parametry_subscrypcji_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;
  @CreatedBy
  @Column(updatable = false)
  private Long createdBy;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  @LastModifiedBy
  private Long lastModifiedBy;
  @Version
  private int version;
  private String pkd;
  private LocalDateTime dataRozpoczeciaOd;
  private LocalDateTime dataRozpoczeciaDo;
  @Column(length = 50)
  private String wojewodztwo;
  @Column(length = 50)
  private String powiat;
  @Column(length = 50)
  private String gmina;
}

