package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "uzytkownicy")
class UzytkownikEntity {

  @Id
  @SequenceGenerator(
          name = "uzytkownicy_seq",
          allocationSize = 1,
          sequenceName = "uzytkownicy_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uzytkownicy_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @EqualsAndHashCode.Include
  private String email;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "podmiot_gosp_id", referencedColumnName = "id")
  private PodmiotGospodarczeViewEntity firma;
}
