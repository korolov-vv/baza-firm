package com.baza.firmy.entity;

import com.baza.firmy.response.CeidgListDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "lista_jdg_pobieranie")
public class ListaJdgPobieranie {

    @Id
    @SequenceGenerator(
            name = "lista_jdg_pobieranie_seq",
            allocationSize = 1,
            sequenceName = "lista_jdg_pobieranie_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lista_jdg_pobieranie_seq")
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private UUID uuid;
    private LocalDateTime createDate;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<CeidgListDto> firmy;
    private Long count;
    private String next;
    private String prev;
    private String self;
    private String first;
    private String last;
    private boolean czyStareDane;
    private boolean czyObsluzona;
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @Builder.Default
    private List<String> nieobsluzoneLinki = new ArrayList<>();
}
