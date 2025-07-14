package com.baza.firmy.danezkrs.domain;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@NoArgsConstructor (force = true)
@AllArgsConstructor
@Entity
@Table (name = "lista_zaktualizowanych_krs")
@EntityListeners(AuditingEntityListener.class)
class ListaZaktualizowanychWpisowKrsEntity {

 @Id
 @SequenceGenerator (
     name = "lista_zaktualizowanych_krs_seq",
     allocationSize = 1,
     sequenceName = "lista_zaktualizowanych_krs_seq")
 @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "lista_zaktualizowanych_krs_seq")
 @EqualsAndHashCode.Include
 private Long id;
 @EqualsAndHashCode.Include
 @Column(updatable = false)
 private UUID uuid;
 @CreatedDate
 @Column(updatable = false)
 private LocalDateTime createDate;
 @LastModifiedDate
 private LocalDateTime lastModifiedDate;
 @EqualsAndHashCode.Include
 @Version
 private int version;

 @Column (columnDefinition = "jsonb")
 @JdbcTypeCode (SqlTypes.JSON)
 private List<String> numeryKrs;
 @Enumerated(EnumType.STRING)
 private StatusPobieraniaEnum statusPobierania;
 @Column (columnDefinition = "jsonb")
 @JdbcTypeCode (SqlTypes.JSON)
 @Builder.Default
 private List<String> nieobsluzoneKrsy = new ArrayList<>();
}
