--liquibase formatted sql
--changeset vkorolov:2026_01_08_19_00_firmy_subscrypcje_init.sql


create table if not exists firmy_subscrypcje (
                                           id BIGINT
                                               constraint firmy_subscrypcje_pk
                                                   primary key,
                                           uuid uuid not null unique,
                                           create_date timestamp,
                                           created_by bigint,
                                           last_modified_date timestamp,
                                           last_modified_by bigint,
                                           version integer NOT NULL DEFAULT 0,
                                           podmiot_gosp_id bigint references podmioty_gospodarcze(id),
                                           subscrypcja_id bigint references subscrypcje(id),
                                           aktywna_od timestamp,
                                           aktywna_do timestamp,
                                           status_subscrypcji varchar(25)
);

create sequence if not exists firmy_subscrypcje_seq increment 1 start 1;
