package com.baza.firmy.danezraportu.entity;

import com.baza.firmy.dto.JdgSzczegolyRaportDto;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@XmlRootElement(name = "tblBusinessEntityStats")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaportDto {

  private List<JdgSzczegolyRaportDto> details;
}
