package com.baza.firmy.danezportaluzewn.domain;

import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ZapiszPortalZewnListUseCase {

    private final PortalZewnRepository portalZewnRepository;
    private final DaneZPortaluZewnMapper daneZPortaluZewnMapper;

    UUID zapisz(DaneZPortaluZewnDto daneZPortaluZewnDto) {
        return portalZewnRepository.save(
                daneZPortaluZewnMapper.toEntity(daneZPortaluZewnDto)
        ).getUuid();
    }
}
