--liquibase formatted sql
--changeset vkorolov:2026_01_12_22_00_firmy_crm_init.sql


create sequence if not exists firmy_crm_seq increment 1 start 1;

create table if not exists firmy_crm (
                                         id BIGINT
                                             constraint firmy_crm_pk
                                                 primary key,
                                         uuid uuid not null unique,
                                         create_date timestamp,
                                         created_by bigint,
                                         last_modified_date timestamp,
                                         last_modified_by bigint,
                                         version integer NOT NULL DEFAULT 0,
                                         firma_klient_id bigint references podmioty_gospodarcze(id),
                                         firma_id bigint references podmioty_gospodarcze(id),
                                         status_kontaktu varchar(25),
                                         sposob_kontaktu varchar(25),
                                         liczba_prob_kontaktu integer,
                                         data_ostatniego_kontaktu timestamp,
                                         data_nastepnego_kontaktu timestamp,
                                         komentarz varchar(255)
);
