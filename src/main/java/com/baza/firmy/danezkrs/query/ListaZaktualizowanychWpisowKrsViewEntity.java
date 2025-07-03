package com.baza.firmy.danezkrs.query;

import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
class ListaZaktualizowanychWpisowKrsViewEntity {

 @Id
 @EqualsAndHashCode.Include
 @Column(insertable = false, updatable = false)
 private Long id;
 @EqualsAndHashCode.Include
 @Column(insertable = false, updatable = false)
 private UUID uuid;
 @CreatedDate
 @Column(updatable = false)
 private LocalDateTime createDate;
 @LastModifiedDate
 @Column(insertable = false, updatable = false)
 private LocalDateTime lastModifiedDate;
 @EqualsAndHashCode.Include
 @Version
 @Column(insertable = false, updatable = false)
 private int version;

 @Column (columnDefinition = "jsonb", insertable = false, updatable = false)
 @JdbcTypeCode (SqlTypes.JSON)
 private List<String> numeryKrs;
 @Enumerated(EnumType.STRING)
 @Column(insertable = false, updatable = false)
 private StatusPobieraniaEnum statusPobierania;
 @Column(insertable = false, updatable = false)
 private boolean czyObsluzona;
 @Column (columnDefinition = "jsonb", insertable = false, updatable = false)
 @JdbcTypeCode (SqlTypes.JSON)
 @Builder.Default
 private List<String> nieobsluzoneKrsy = new ArrayList<>();
}
