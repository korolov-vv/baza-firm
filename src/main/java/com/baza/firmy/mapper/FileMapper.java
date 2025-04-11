package com.baza.firmy.mapper;

import com.baza.firmy.dto.FileDto;
import com.baza.firmy.entity.FileEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { UUID.class, LocalDateTime.class })
public interface FileMapper {

  @Mapping (target = "uuid", expression = "java(UUID.randomUUID())")
  @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
  FileEntity toFileEntity(FileDto fileDto);

  FileDto toFileDto(FileEntity fileEntity);
}
