package com.baza.firmy.firmysubscrypcje.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
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
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "parametry_subscrypcji")
@EntityListeners(AuditingEntityListener.class)
public class ParametrySubscrypcjiViewEntity {

  @Id
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @CreatedDate
  @Column(insertable = false, updatable = false)
  private LocalDateTime createDate;
  @CreatedBy
  @Column(insertable = false, updatable = false)
  private Long createdBy;
  @LastModifiedDate
  @Column(insertable = false, updatable = false)
  private LocalDateTime lastModifiedDate;
  @LastModifiedBy
  @Column(insertable = false, updatable = false)
  private Long lastModifiedBy;
  @Version
  @Column(insertable = false, updatable = false)
  private int version;
  @Column(insertable = false, updatable = false)
  private String pkd;
  @Column(insertable = false, updatable = false)
  private LocalDateTime dataRozpoczeciaOd;
  @Column(insertable = false, updatable = false)
  private LocalDateTime dataRozpoczeciaDo;
  @Column(insertable = false, updatable = false)
  private String wojewodztwo;
  @Column(insertable = false, updatable = false)
  private String powiat;
  @Column(insertable = false, updatable = false)
  private String gmina;

  public Optional<String> getPkd() {
    return Optional.ofNullable(pkd);
  }

  public Optional<LocalDateTime> getDataRozpoczeciaOd() {
    return Optional.ofNullable(dataRozpoczeciaOd);
  }

  public Optional<LocalDateTime> getDataRozpoczeciaDo() {
    return Optional.ofNullable(dataRozpoczeciaDo);
  }

  public Optional<String> getWojewodztwo() {
    return Optional.ofNullable(wojewodztwo);
  }

  public Optional<String> getPowiat() {
    return Optional.ofNullable(powiat);
  }

  public Optional<String> getGmina() {
    return Optional.ofNullable(gmina);
  }
}

