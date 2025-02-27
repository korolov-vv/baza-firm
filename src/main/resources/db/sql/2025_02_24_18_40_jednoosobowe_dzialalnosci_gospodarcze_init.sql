--liquibase formatted sql
--changeset vkorolov:2025_02_24_18_40_jednoosobowe_dzialalnosci_gospodarcze_init.sql


create table if not exists jednoosobowe_dzialalnosci_gospodarcze (
                        id BIGINT
                            constraint jednoosobowe_dzialalnosci_gospodarcze_pk
                                primary key,
                        uuid uuid not null unique,
                        create_date timestamp,
                        created_by bigint,
                        last_modified_date timestamp,
                        last_modified_by bigint,
                        version_id integer NOT NULL DEFAULT 0,
                        nazwa varchar(255) not null,
                        ceidg_id uuid not null unique,
                        adres_dzialalnosci_id bigint,
                        adres_korespondencyjny_id bigint,
                        wlasciciel_id bigint,
                        pkd_glowny varchar(50),
                        spolki jsonb,
                        data_rozpoczecia varchar(20),
                        data_zawieszenia varchar(20),
                        data_zakonczenia varchar(20),
                        data_wykreslenia varchar(20),
                        data_wznowienia varchar(20),
                        status varchar(50),
                        numer_statusu int,
                        telefon varchar(50),
                        email varchar(255),
                        www varchar(255),
                        adres_doreczen_elektronicznych varchar(255),
                        inna_forma_kontaktu varchar(255),
                        pelne_info jsonb,
                        link varchar(255),
                        czy_pobrano_szczegoly boolean default false
);


alter table jednoosobowe_dzialalnosci_gospodarcze
    add constraint FK_adres_dzialalnosci_id foreign key (adres_dzialalnosci_id) REFERENCES adresy,
    add constraint FK_adres_korespondencyjny_id foreign key (adres_korespondencyjny_id) REFERENCES adresy,
    add constraint FK_wlasciciel_id foreign key (wlasciciel_id) REFERENCES osoby;


create sequence if not exists jednoosobowe_dzialalnosci_gospodarcze_seq increment 1 start 1;
