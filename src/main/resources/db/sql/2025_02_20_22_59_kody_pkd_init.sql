--liquibase formatted sql
--changeset vkorolov:2025_02_20_22_59_kody_pkd_init.sql


create table if not exists kody_pkd (
                        id BIGINT
                            constraint kody_pkd_pk
                                primary key,
                        uuid uuid not null unique,
                        create_date timestamp,
                        created_by bigint,
                        last_modified_date timestamp,
                        last_modified_by bigint,
                        version_id integer NOT NULL DEFAULT 0,
                        kod varchar(50) not null
);

create sequence if not exists kody_pkd_seq increment 1 start 1;
