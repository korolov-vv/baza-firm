package com.baza.firmy.subscrypcje.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import com.baza.firmy.subscrypcje.query.SubscrypcjaViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "firmy_subscrypcje")
class UzytkownikSubscrypcjaEntity {

  @Id
  @SequenceGenerator(
          name = "uzytkownicy_subscrypcje_seq",
          allocationSize = 1,
          sequenceName = "uzytkownicy_subscrypcje_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uzytkownicy_subscrypcje_seq")
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
  @JoinColumn(name = "podmiot_gosp_id", referencedColumnName = "id")
  private PodmiotGospodarczeViewEntity firmaKlient;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "subscrypcja_id", referencedColumnName = "id")
  private SubscrypcjaViewEntity subscrypcja;
  private LocalDate aktywnaOd;
  private LocalDate aktywnaDo;
  @Enumerated(EnumType.STRING)
  private StatusSubscrypcji statusSubscrypcji;
}
