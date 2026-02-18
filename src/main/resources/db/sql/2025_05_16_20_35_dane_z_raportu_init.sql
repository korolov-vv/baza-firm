--liquibase formatted sql
--changeset vkorolov:2025_05_16_20_35_dane_z_raportu_init.sql


create table if not exists dane_z_raportu (
    id BIGINT
        constraint dane_z_raportu_pk
            primary key,
    uuid UUID NOT NULL,
    create_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    version INT NOT NULL,
    wojewodztwo VARCHAR(255) NOT NULL,
    dzialalnosci JSONB NOT NULL
);

create sequence if not exists dane_z_raportu_seq increment 1 start 1;
