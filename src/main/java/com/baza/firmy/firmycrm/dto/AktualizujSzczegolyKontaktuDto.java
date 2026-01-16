package com.baza.firmy.firmycrm.dto;

import com.baza.firmy.firmycrm.domain.SposobKontaktu;
import com.baza.firmy.firmycrm.domain.StatusKontaktu;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AktualizujSzczegolyKontaktuDto {

  @NotNull(message = "UUID firmy jest obowiązkowe")
  private UUID uuidFirmy;

  @NotNull(message = "Wersja jest obowiązkowa")
  private Integer version;

  @NotNull(message = "Status kontaktu jest obowiązkowy")
  private StatusKontaktu statusKontaktu;

  @NotNull(message = "Sposób kontaktu jest obowiązkowy")
  private SposobKontaktu sposobKontaktu;

  @NotNull(message = "Data ostatniego kontaktu jest obowiązkowa")
  private LocalDateTime dataOstatniegoKontaktu;

  private LocalDateTime dataNastepnegoKontaktu;

  private String komentarz;
}

