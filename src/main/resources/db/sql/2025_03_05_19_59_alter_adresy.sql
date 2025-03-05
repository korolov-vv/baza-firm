--liquibase formatted sql
--changeset vkorolov:2025_02_20_22_32_adresy_init


alter table adresy
    add column lokal varchar(50);
