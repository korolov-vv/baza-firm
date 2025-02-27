--liquibase formatted sql
--changeset vkorolov:2025_02_20_22_32_adresy_init


create table if not exists adresy (
                        id BIGINT
                            constraint adresy_pk
                                primary key,
                        uuid uuid not null unique,
                        create_date timestamp,
                        created_by bigint,
                        last_modified_date timestamp,
                        last_modified_by bigint,
                        version_id integer NOT NULL DEFAULT 0,
                        kraj varchar(255) not null,
                        wojewodztwo varchar(255),
                        powiat varchar(255),
                        gmina varchar(255),
                        miasto varchar(255),
                        ulica varchar(255),
                        budynek varchar(50),
                        kod_pocztowy varchar(20),
                        terc varchar(20),
                        simc varchar(20),
                        ulic varchar(20)
);

create sequence if not exists adresy_seq increment 1 start 1;
