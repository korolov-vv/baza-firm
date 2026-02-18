--liquibase formatted sql
--changeset vkorolov:2026_01_05_21_00_uzytkownicy_init.sql

create table if not exists uzytkownicy (
                                                    id BIGINT
                                                        constraint uzytkownicy_pk
                                                            primary key,
                                                    uuid uuid not null unique,
                                                    create_date timestamp,
                                                    created_by bigint,
                                                    last_modified_date timestamp,
                                                    last_modified_by bigint,
                                                    version integer NOT NULL DEFAULT 0,
                                                    email varchar(255),
                                                    podmiot_gosp_id bigint references podmioty_gospodarcze(id)
);

create sequence if not exists uzytkownicy_seq increment 1 start 1;
