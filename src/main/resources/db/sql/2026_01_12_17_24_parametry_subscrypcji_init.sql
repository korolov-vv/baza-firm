--liquibase formatted sql
--changeset vkorolov:2026_01_12_17_24_parametry_subscrypcji_init.sql

create table if not exists parametry_subscrypcji (
                                           id BIGINT
                                               constraint parametry_subscrypcji_pk
                                                   primary key,
                                           uuid uuid not null unique,
                                           create_date timestamp,
                                           created_by bigint,
                                           last_modified_date timestamp,
                                           last_modified_by bigint,
                                           version integer NOT NULL DEFAULT 0,
                                           pkd varchar,
                                           data_rozpoczecia_od timestamp,
                                           data_rozpoczecia_do timestamp,
                                           wojewodztwo varchar(50),
                                           powiat varchar(50),
                                           gmina varchar(50)
);

create sequence if not exists parametry_subscrypcji_seq increment 1 start 1;

alter table firmy_subscrypcje
    add column if not exists parametry_subscrypcji_id bigint references parametry_subscrypcji(id);
