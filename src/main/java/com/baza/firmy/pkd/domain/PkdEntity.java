package com.baza.firmy.pkd.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "kody_pkd")
class PkdEntity {

    @Id
    @SequenceGenerator(
            name = "kody_pkd_seq",
            allocationSize = 1,
            sequenceName = "kody_pkd_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kody_pkd_seq")
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private UUID uuid;
    private String kod;
    private String nazwa;


    public Optional<String> getNazwa() {
        return Optional.ofNullable(nazwa);
    }
}
