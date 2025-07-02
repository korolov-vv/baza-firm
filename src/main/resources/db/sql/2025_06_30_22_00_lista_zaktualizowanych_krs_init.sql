--liquibase formatted sql
--changeset vkorolov:2025_06_30_22_00_lista_zaktualizowanych_krs_init.sql

create table if not exists lista_zaktualizowanych_krs (
                                                    id BIGINT
                                                        constraint lista_zaktualizowanych_krs_pk
                                                            primary key,
                                                    uuid uuid not null unique,
                                                    create_date timestamp,
                                                    created_by bigint,
                                                    last_modified_date timestamp,
                                                    last_modified_by bigint,
                                                    version integer NOT NULL DEFAULT 0,
                                                    numery_krs jsonb,
                                                    czy_obsluzona boolean default false,
                                                    nieobsluzone_krsy jsonb
);

create sequence if not exists lista_zaktualizowanych_krs_seq increment 1 start 1;
