--liquibase formatted sql
--changeset vkorolov:2026_01_07_19_00_uzytkownicy_subscrypcje_init.sql


create table if not exists uzytkownicy_subscrypcje (
                                           id BIGINT
                                               constraint uzytkownicy_subscrypcje_pk
                                                   primary key,
                                           uuid uuid not null unique,
                                           create_date timestamp,
                                           created_by bigint,
                                           last_modified_date timestamp,
                                           last_modified_by bigint,
                                           version integer NOT NULL DEFAULT 0,
                                           uzytkownik_id bigint references uzytkownicy(id),
                                           subscrypcja_id bigint references subscrypcje(id),
                                           aktywna_od timestamp,
                                           aktywna_do timestamp,
                                           czy_oplacona boolean
);

create sequence if not exists uzytkownicy_subscrypcje_seq increment 1 start 1;
