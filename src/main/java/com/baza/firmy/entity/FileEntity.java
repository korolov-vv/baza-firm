package com.baza.firmy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table (name = "files")
public class FileEntity {

  @Id
  @SequenceGenerator (
      name = "files_seq",
      allocationSize = 1,
      sequenceName = "files_seq")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_seq")
  @EqualsAndHashCode.Include
  private Long id;
  @Version
  private int version;
  @EqualsAndHashCode.Include
  private UUID uuid;
  private LocalDateTime createDate;

  @Column(name = "file_name", nullable = false)
  private String fileName;
  @Column(name = "path", nullable = false)
  private String path;
  @Column(name = "extention", nullable = false)
  private String extention;
  @Column (name = "size", nullable = false)
  private Long size;
}
