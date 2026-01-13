package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczyViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table (name = "uzytkownicy")
@EntityListeners(AuditingEntityListener.class)
class UzytkownikEntity {

  @Id
  @SequenceGenerator(
          name = "uzytkownicy_seq",
          allocationSize = 1,
          sequenceName = "uzytkownicy_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uzytkownicy_seq")
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
  @EqualsAndHashCode.Include
  private String email;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "podmiot_gosp_id", referencedColumnName = "id")
  private PodmiotGospodarczyViewEntity firma;
  private boolean czyEmailPotwierdzony;
}
