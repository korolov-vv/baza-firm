package com.baza.firmy.firmycrm.query;

import com.baza.firmy.firmycrm.domain.SposobKontaktu;
import com.baza.firmy.firmycrm.domain.StatusKontaktu;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
public class FirmaCrmViewEntity {

  @Id
  @EqualsAndHashCode.Include
  private Long id;
  @CreatedDate
  @Column(insertable = false, updatable = false)
  private LocalDateTime createDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
  @EqualsAndHashCode.Include
  @Version
  @Column(insertable = false, updatable = false)
  private int version;
  @EqualsAndHashCode.Include
  @Column(insertable = false, updatable = false)
  private UUID uuid;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "firma_klient_id", referencedColumnName = "id", insertable = false, updatable = false)
  private PodmiotGospodarczeViewEntity firmaKlient;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "firma_id", referencedColumnName = "id", insertable = false, updatable = false)
  private PodmiotGospodarczeViewEntity firmaCrm;
  @Enumerated(EnumType.STRING)
  @Column(insertable = false, updatable = false)
  private StatusKontaktu statusKontaktu;
  @Enumerated(EnumType.STRING)
  @Column(insertable = false, updatable = false)
  private SposobKontaktu sposobKontaktu;
  @Column(insertable = false, updatable = false)
  private int liczbaProbKontaktu;
  @Column(insertable = false, updatable = false)
  private LocalDateTime dataOstatniegoKontaktu;
  @Column(insertable = false, updatable = false)
  private LocalDateTime dataNastepnegoKontaktu;
  @Column(insertable = false, updatable = false)
  private String komentarz;
}
