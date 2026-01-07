package com.baza.firmy.uzytkownicy.query;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "uzytkownicy")
public class UzytkownikViewEntity {

  @Id
  @EqualsAndHashCode.Include
  private Long id;
  @Column(updatable = false)
  private LocalDateTime createDate;
  @Column(updatable = false)
  private LocalDateTime lastModifiedDate;
  @EqualsAndHashCode.Include
  @Column(updatable = false)
  private int version;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private String email;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "podmiot_gosp_id", referencedColumnName = "id")
  private PodmiotGospodarczeViewEntity firma;
}
