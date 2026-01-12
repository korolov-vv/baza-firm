package com.baza.firmy.subscrypcjeuzytkownika.query;

import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import com.baza.firmy.subscrypcje.domain.StatusSubscrypcji;
import com.baza.firmy.subscrypcje.query.SubscrypcjaViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
class UzytkownikSubscrypcjaViewEntity {

  @Id
  @EqualsAndHashCode.Include
  private Long id;
  @CreatedDate
  @Column(updatable = false, insertable = false)
  private LocalDateTime createDate;
  @LastModifiedDate
  @Column(insertable = false, updatable = false)
  private LocalDateTime lastModifiedDate;
  @EqualsAndHashCode.Include
  @Version
  @Column(insertable = false, updatable = false)
  private int version;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "podmiot_gosp_id", referencedColumnName = "id", insertable = false, updatable = false)
  private PodmiotGospodarczeViewEntity firmaKlienta;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "subscrypcja_id", referencedColumnName = "id", insertable = false, updatable = false)
  private SubscrypcjaViewEntity subscrypcja;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parametry_subscrypcji_id", referencedColumnName = "id", insertable = false, updatable = false)
  private ParametrySubscrypcjiViewEntity parametrySubscrypcji;
  @Column(insertable = false, updatable = false)
  private LocalDate aktywnaOd;
  @Column(insertable = false, updatable = false)
  private LocalDate aktywnaDo;
  @Enumerated(EnumType.STRING)
  @Column(insertable = false, updatable = false)
  private StatusSubscrypcji statusSubscrypcji;
}
