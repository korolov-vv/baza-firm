package com.baza.firmy.service;

import com.baza.firmy.entity.JednoosobowaDzialalnoscGospodarcza;
import com.baza.firmy.mapper.JednoosobowaDzialalnoscGospodarczaMapper;
import com.baza.firmy.repository.JednoosobowaDzialalnoscGospodarczaRepository;
import com.baza.firmy.response.CeidgListDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JednoosobowaDzialalnoscGospodarczaService {
  
  private final JednoosobowaDzialalnoscGospodarczaMapper jednoosobowaDzialalnoscGospodarczaMapper;
  private final JednoosobowaDzialalnoscGospodarczaRepository jednoosobowaDzialalnoscGospodarczaRepository;
  
  public List<UUID> saveBusinesses(List<CeidgListDto> jdgListDtos) {
    return jednoosobowaDzialalnoscGospodarczaMapper.toEntityList(jdgListDtos).stream()
        .peek(jdg -> {
          if (jednoosobowaDzialalnoscGospodarczaRepository.existsByCeidgId(jdg.getCeidgId())) {
            jednoosobowaDzialalnoscGospodarczaRepository.save(jdg);
          }
        })
        .map(JednoosobowaDzialalnoscGospodarcza::getCeidgId)
        .toList();
  }
}
