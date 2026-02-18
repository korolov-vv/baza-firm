package com.baza.firmy.common.service;

import com.baza.firmy.configuration.properties.PortalZewnProperties;
import com.baza.firmy.constants.enums.StatusPobieraniaEnum;
import com.baza.firmy.danezportaluzewn.domain.DaneZPortaluZewnFacade;
import com.baza.firmy.danezportaluzewn.domain.dto.DaneZPortaluZewnDto;
import com.baza.firmy.danezportaluzewn.domain.dto.FirmaPortalZewnDto;
import com.baza.firmy.danezportaluzewn.query.DaneZPortaluZewnQueryFacade;
import com.baza.firmy.danezportaluzewn.query.dto.PortalZewnKategoriaDto;
import com.baza.firmy.podmiotygospodarcze.domain.Rejestr;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortalZewnParser {

    private final PortalZewnProperties properties;
    private final DaneZPortaluZewnFacade daneZPortaluZewnFacade;
    private final DaneZPortaluZewnQueryFacade daneZPortaluZewnQueryFacade;

    public void parse() {
        int page = 1;
        int companiesCount = 0;

        PortalZewnKategoriaDto kategoriaDto = daneZPortaluZewnQueryFacade.pobierzPierwszaNiepodjetaKategorie()
                .orElseThrow(() -> new RuntimeException("Brak niepodjętych kategorii do przetworzenia."));

        log.info("Rozpoczynam parsowanie danych z portalu zewnętrznego dla Kategorii: " + kategoriaDto.getKategoria() + ".");

        daneZPortaluZewnFacade.zmienStatusPobierzniaKategorii(kategoriaDto.getUuid(), StatusPobieraniaEnum.W_TRAKCIE);

        String kategoriaPath = kategoriaDto.getPath();
        String bierzacaStrona = properties.getUrl()
                .concat(kategoriaPath)
                .concat(page == 1 ? "?count=100" : "/" + page + "?count=100");

        do {
            String nastepnaStrona = properties.getUrl()
                    .concat(kategoriaPath)
                    .concat("/")
                    .concat(String.valueOf(++page))
                    .concat("?count=100");

            log.info("Parsuję stronę: " + bierzacaStrona + "; \n Nastepna strona: " + nastepnaStrona + ".");

            DaneZPortaluZewnDto.DaneZPortaluZewnDtoBuilder daneZPortaluZewnDtoBuilder = DaneZPortaluZewnDto.builder()
                    .uuid(UUID.randomUUID())
                    .pageLink(bierzacaStrona)
                    .statusPobierania(StatusPobieraniaEnum.NIEPODJETE)
                    .niepobraneFirmy(new ArrayList<>());

            List<FirmaPortalZewnDto> listaFirmNaStronie = new ArrayList<>();

            try {
                Document doc = Jsoup.connect(bierzacaStrona).get();
                Elements companies = doc.select(".catalog-row-container");
                companiesCount = companies.size();

                if (companiesCount == 0) {
                    return;
                }

                if (companiesCount == 100) {
                    daneZPortaluZewnDtoBuilder.nextPageLink(nastepnaStrona);
                    bierzacaStrona = nastepnaStrona;
                }

                for (Element company : companies) {
                    Element nazwaElement = company.selectFirst(".catalog-row-first-line__company-name");
                    String nazwa = nazwaElement != null ? nazwaElement.text() : Strings.EMPTY;
                    String rejestr = company.select(".catalog-row-first-line__registry-type").text().toUpperCase();
                    String krs = company.select(".text-text").select(".krs").text();
                    String nip = company.select(".text-text").select(".tax-id").text();
                    String regon = company.select(".text-text").select(".regon").text();
                    String link = nazwaElement != null ? nazwaElement.attr("href") : Strings.EMPTY;
                    String adres = company.select(".catalog-row-company-info__address").select(".whitespace-nowrap").text();

                    listaFirmNaStronie.add(
                            FirmaPortalZewnDto.builder()
                                    .nazwa(nazwa)
                                    .rejestr(Rejestr.valueOf(rejestr))
                                    .krs(krs)
                                    .nip(nip)
                                    .regon(regon)
                                    .link(properties.getUrl().concat("/").concat(link))
                                    .adres(adres)
                                    .build()
                    );
                }
                daneZPortaluZewnDtoBuilder.firmy(listaFirmNaStronie);
                log.info("Koniec parsowania strony: " + bierzacaStrona + ".");
            } catch (Exception e) {
                daneZPortaluZewnDtoBuilder.blad(e.getMessage());
                daneZPortaluZewnFacade.zmienStatusPobierzniaKategorii(kategoriaDto.getUuid(), StatusPobieraniaEnum.ZAKONCZONE_Z_BLENDAMI);
            }

            daneZPortaluZewnFacade.zapiszDaneZPortaluZewn(daneZPortaluZewnDtoBuilder.build());
            daneZPortaluZewnFacade.zmienStatusPobierzniaKategorii(kategoriaDto.getUuid(), StatusPobieraniaEnum.ZAKONCZONE);
        } while (companiesCount == 100);
        log.info("Koniec parsowania danych z portalu zewnętrznego dla Kategorii: " + kategoriaDto.getKategoria() + ".");
    }
}
