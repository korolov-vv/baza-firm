--liquibase formatted sql
--changeset vkorolov:2025_02_20_22_35_kraje_init.sql


create table if not exists kraje (
                        id BIGINT
                            constraint kraje_pk
                                primary key,
                        uuid uuid not null unique,
                        create_date timestamp,
                        created_by bigint,
                        last_modified_date timestamp,
                        last_modified_by bigint,
                        version_id integer NOT NULL DEFAULT 0,
                        symbol varchar(50),
                        kraj varchar(255) not null
);

create sequence if not exists kraje_seq increment 1 start 1;
