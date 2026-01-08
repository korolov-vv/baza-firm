package com.baza.firmy.uzytkownicy.domain;

import com.baza.firmy.common.util.MailSenderUtills;
import com.baza.firmy.podmiotygospodarcze.domain.PodmiotyGospodarczeFacade;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotGospodarczeViewEntity;
import com.baza.firmy.podmiotygospodarcze.query.PodmiotyGospodarczeQueryFacade;
import com.baza.firmy.subscrypcje.domain.SubscrypcjeFacade;
import com.baza.firmy.uzytkownicy.dto.StworzUzytkownikaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class StworzUzytkownikaUseCase {

  private final UzytkownicyRepository uzytkownicyRepository;
  private final UzytkownicyMapper uzytkownicyMapper;
  private final SubscrypcjeFacade subscrypcjeFacade;
  private final PodmiotyGospodarczeQueryFacade podmiotyGospodarczeQueryFacade;
  private final PodmiotyGospodarczeFacade podmiotyGospodarczeFacade;
  private final MailSenderUtills mailSenderUtills;

  public UUID stworzUzytkownika(StworzUzytkownikaDto stworzUzytkownikaDto) {
    if (uzytkownicyRepository.existsByFirmaNip(stworzUzytkownikaDto.getNip())) {
      // TODO: zrobić service do notyfikacjii o takich zdarzeniach
      mailSenderUtills.sendEmail(
              "vadymkorolov@gmail.com",
              "Próba tworzenia więcej niż jednego użytkownika dla firmy",
              "Istnieje już uzytkownik dla firmy z podanym NIP: " + stworzUzytkownikaDto.getNip()
      );
      throw new IllegalArgumentException("Istnieje już uzytkownik dla firmy z podanym NIP: " + stworzUzytkownikaDto.getNip());
    }

    UzytkownikEntity uzytkownikEntity = uzytkownicyMapper.toUzytkownikEntity(stworzUzytkownikaDto);
    uzytkownikEntity.setFirma(pobierzDaneFirmy(stworzUzytkownikaDto.getNip()));

    UUID uuidZapisanegoUzytkownika = uzytkownicyRepository.save(uzytkownikEntity).getUuid();

    subscrypcjeFacade.stworzTrialUzytkownika(uuidZapisanegoUzytkownika);

    return uuidZapisanegoUzytkownika;
  }

  private PodmiotGospodarczeViewEntity pobierzDaneFirmy(String nip) {
    return podmiotyGospodarczeQueryFacade.pobierzAktywnaFirmePoNip(nip)
            .orElseGet(() -> {
              UUID uuidNowegoPodmiotu = podmiotyGospodarczeFacade.pobierzOrazZapiszDaneFirmyZGus(nip);
              return podmiotyGospodarczeQueryFacade.pobierzPoUuid(uuidNowegoPodmiotu)
                      .orElseThrow(() -> new IllegalStateException("Nie udało się pobrać nowo utworzonego podmiotu gospodarczego o UUID: " + uuidNowegoPodmiotu));
            });
  }
}
