package com.baza.firmy.pkd.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "kody_pkd")
public class PkdViewEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private Long id;
    @EqualsAndHashCode.Include
    @Column(insertable = false, updatable = false)
    private UUID uuid;
    @Column(insertable = false, updatable = false)
    private String kod;
    @Column(insertable = false, updatable = false)
    private String nazwa;

    public Optional<String> getNazwa() {
        return Optional.ofNullable(nazwa);
    }
}
