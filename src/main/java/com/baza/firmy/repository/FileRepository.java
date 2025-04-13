package com.baza.firmy.repository;

import com.baza.firmy.entity.FileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

  Optional<FileEntity> findFirstByOrderByIdDesc();
}
