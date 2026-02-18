--liquibase formatted sql
--changeset vkorolov:2025_09_08_22_39_alter_kody_pkd.sql

alter table kody_pkd
    add column if not exists nazwa varchar;
