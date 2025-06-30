package com.baza.firmy.entity;

import com.baza.firmy.response.CeidgListDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Builder
@NoArgsConstructor (force = true)
@AllArgsConstructor
@Entity
@Table (name = "lista_jdg_pobieranie")
public class ListaJdgPobieranie {

 @Id
 @SequenceGenerator (
     name = "lista_jdg_pobieranie_seq",
     allocationSize = 1,
     sequenceName = "lista_jdg_pobieranie_seq")
 @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "lista_jdg_pobieranie_seq")
 @EqualsAndHashCode.Include
 private Long id;
 @EqualsAndHashCode.Include
 private UUID uuid;
 private LocalDateTime createDate;

 @Column (columnDefinition = "jsonb")
 @JdbcTypeCode (SqlTypes.JSON)
 private List<CeidgListDto> firmy;
 private Long count;
 private String next;
 private String prev;
 private String self;
 private String first;
 private String last;
 private boolean czyStareDane;
 private boolean czyObsluzona;
 @Column (columnDefinition = "jsonb")
 @JdbcTypeCode (SqlTypes.JSON)
 private List<String> nieobsluzoneLinki;
}
