--liquibase formatted sql
--changeset vkorolov:2025_02_20_22_55_osoby_init.sql


create table if not exists osoby (
                        id BIGINT
                            constraint osoby_pk
                                primary key,
                        uuid uuid not null unique,
                        create_date timestamp,
                        created_by bigint,
                        last_modified_date timestamp,
                        last_modified_by bigint,
                        version_id integer NOT NULL DEFAULT 0,
                        pesel varchar(20) unique,
                        nip varchar(20) unique,
                        regon varchar(20),
                        imie varchar(100),
                        nazwisko varchar(100)
);

create sequence if not exists osoby_seq increment 1 start 1;
