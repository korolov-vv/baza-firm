--liquibase formatted sql
--changeset vkorolov:2026_01_07_19_00_subscrypcje_init.sql


create table if not exists subscrypcje (
                                           id BIGINT
                                               constraint subscrypcje_pk
                                                   primary key,
                                           uuid uuid not null unique,
                                           create_date timestamp,
                                           created_by bigint,
                                           last_modified_date timestamp,
                                           last_modified_by bigint,
                                           version integer NOT NULL DEFAULT 0,
                                           nazwa varchar(255),
                                           opis text,
                                           ilosc_dostepnych_firm varchar(255),
                                           okres_trwania_subskrypcji_w_dniach integer,
                                           czy_aktywna boolean default true
);

create sequence if not exists subscrypcje_seq increment 1 start 1;

INSERT INTO subscrypcje (id, uuid, create_date, created_by, last_modified_date, last_modified_by, version, nazwa, opis, ilosc_dostepnych_firm, okres_trwania_subskrypcji_w_dniach, czy_aktywna)
VALUES (nextval('subscrypcje_seq'), gen_random_uuid(), NOW(), 1, NOW(), 1, 0, 'TRIAL', 'Trial subscription with 14 days duration and 100 available companies', '100', 14, true);

INSERT INTO subscrypcje (id, uuid, create_date, created_by, last_modified_date, last_modified_by, version, nazwa, opis, ilosc_dostepnych_firm, okres_trwania_subskrypcji_w_dniach, czy_aktywna)
VALUES (nextval('subscrypcje_seq'), gen_random_uuid(), NOW(), 1, NOW(), 1, 0, 'SCHRACK', 'Specialna subscrypcjadla Schrack Technik', '99999999', 999999999, true);
