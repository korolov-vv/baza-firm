package com.baza.firmy.danezraportu.entity;

import com.baza.firmy.dto.JdgSzczegolyRaportDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "dane_z_raportu")
@EntityListeners(AuditingEntityListener.class)
class DaneZRaportuEntity {

  @Id
  @SequenceGenerator(
      name = "dane_z_raportu_seq",
      allocationSize = 1,
      sequenceName = "dane_z_raportu_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dane_z_raportu_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @EqualsAndHashCode.Include
  private UUID uuid;
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  @Version
  private int version;

  private String wojewodztwo;

  @Builder.Default
  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<JdgSzczegolyRaportDto> dzialalnosci = new ArrayList<>();
}
