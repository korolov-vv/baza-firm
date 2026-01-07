package com.baza.firmy.subscrypcje.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "subscrypcje")
class SubscrypcjaEntity {

  @Id
  @SequenceGenerator(
          name = "subscrypcje_seq",
          allocationSize = 1,
          sequenceName = "subscrypcje_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscrypcje_seq")
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
  private String nazwa;
  private String opis;
  private int iloscDostepnychFirm;
  @Column(name = "okres_trwania_subskrypcji_w_dniach")
  private int okresTrwaniaWDniach;
}
