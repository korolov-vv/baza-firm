package com.baza.firmy.podmiotygospodarcze.domain;

import com.baza.firmy.constants.enums.BusinessStatus;
import com.baza.firmy.podmiotygospodarcze.domain.dto.GusSzczegolyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
class StworzPodmiotGospodarczyZGusUseCase {

  private final PodmiotyGospodarczeRepository podmiotyGospodarczeRepository;

  public UUID stworzPodmiotGospodarczy(GusSzczegolyDto gusSzczegolyDto) {
    PodmiotGospodarczyEntity podmiotGospodarczyEntity = PodmiotGospodarczyEntity.builder()
            .uuid(UUID.randomUUID())
            .nip(gusSzczegolyDto.getNip())
            .status(BusinessStatus.AKTYWNY)
            .build();
    return podmiotyGospodarczeRepository.save(podmiotGospodarczyEntity).getUuid();
  }
}
