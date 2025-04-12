package com.baza.firmy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FileDto {
  private Long id;
  private int version;
  private String fileName;
  private String path;
  private String extention;
  private Long size;
}
