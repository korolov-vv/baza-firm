--liquibase formatted sql
--changeset vkorolov:2025_04_11_22_24_files_init.sql


create table if not exists files (
                        id BIGINT
                            constraint files_pk
                                primary key,
                        uuid uuid not null unique,
                        version integer NOT NULL DEFAULT 0,
                        create_date timestamp,
                        file_name varchar(255) NOT NULL,
                        path varchar(255) NOT NULL,
                        extention varchar(10) NOT NULL,
                        size integer NOT NULL
);

create sequence if not exists files_seq increment 1 start 1;
