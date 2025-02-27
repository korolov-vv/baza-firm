--liquibase formatted sql
--changeset vkorolov:2025_02_25_17_24_lista_jdg_pobieranie_init.sql


create table if not exists lista_jdg_pobieranie (
                        id BIGINT
                            constraint lista_jdg_pobieranie_pk
                                primary key,
                        uuid uuid not null unique,
                        create_date timestamp,
                        created_by bigint,
                        last_modified_date timestamp,
                        last_modified_by bigint,
                        version_id integer NOT NULL DEFAULT 0,
                        firmy jsonb,
                        count bigint,
                        next varchar(255),
                        prev varchar(255),
                        self varchar(255),
                        first varchar(255),
                        last varchar(255),
                        czy_obsluzona boolean default false
);

create sequence if not exists lista_jdg_pobieranie_seq increment 1 start 1;
