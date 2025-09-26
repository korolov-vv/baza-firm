--liquibase formatted sql
--changeset vkorolov:2025_09_26_17_03_portal_zewn_kategorie_init.sql


create table if not exists portal_zewn_kategorie (
    id BIGINT
        constraint portal_list_pk
            primary key,
    uuid uuid not null unique,
    create_date timestamp,
    created_by bigint,
    last_modified_date timestamp,
    last_modified_by bigint,
    version integer NOT NULL DEFAULT 0,
    kategoria varchar,
    path varchar,
    status_pobierania varchar(255),
    blad text
);

create sequence if not exists portal_zewn_kategorie_seq increment 1 start 1;

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Artykuły dla biur i wyposażenie biurowe', '/pl/firmy/artykuly-dla-biur-i-wyposazenie-biurowe', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'BHP, PPOŻ i środki czystości', '/pl/firmy/bhp-ppoz-i-srodki-czystosci', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Budownictwo, usługi i materiały budowlane', '/pl/firmy/budownictwo-uslugi-i-materialy-budowlane', 'ZAKONCZONE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Chemia', '/pl/firmy/chemia', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Dom i ogród', '/pl/firmy/dom-i-ogrod', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Edukacja, sport i rozrywka', '/pl/firmy/edukacja-sport-i-rozrywka', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Energia, paliwa, media', '/pl/firmy/energia-paliwa-media', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'IT i telekomunikacja', '/pl/firmy/it-i-telekomunikacja', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Logistyka, spedycja, transport', '/pl/firmy/logistyka-spedycja-transport', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Marketing, reklama i PR', '/pl/firmy/marketing-reklama-i-pr', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Maszyny i utrzymanie ciągłości produkcji', '/pl/firmy/maszyny-i-utrzymanie-ciaglosci-produkcji', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Meble', '/pl/firmy/meble', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Medycyna, farmacja i kosmetyki', '/pl/firmy/medycyna-farmacja-i-kosmetyki', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Nieruchomości i usługi powiązane', '/pl/firmy/nieruchomosci-i-uslugi-powiazane', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Odpady i surowce wtórne', '/pl/firmy/odpady-i-surowce-wtorne', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Opakowania, etykiety, folie, taśmy', '/pl/firmy/opakowania-etykiety-folie-tasmy', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Pojazdy i środki transportu', '/pl/firmy/pojazdy-i-srodki-transportu', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Pozostałe', '/pl/firmy/pozostale', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Przemysł spożywczy', '/pl/firmy/przemysl-spozywczy', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Rolnictwo, hodowla i uprawa', '/pl/firmy/rolnictwo-hodowla-i-uprawa', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'RTV i AGD', '/pl/firmy/rtv-i-agd', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Surowce i półprodukty', '/pl/firmy/surowce-i-polprodukty', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Ubrania, obuwie, galanteria, biżuteria', '/pl/firmy/ubrania-obuwie-galanteria-bizuteria', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Usługi dla firm', '/pl/firmy/uslugi-dla-firm', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Administracja publiczna', '/pl/firmy/administracja-publiczna', 'NIEPODJETE');

insert into portal_zewn_kategorie (id, uuid, create_date, kategoria, path, status_pobierania)
values (nextval('portal_zewn_kategorie_seq'), gen_random_uuid(), now(), 'Inne', '/pl/firmy/inne', 'NIEPODJETE');
