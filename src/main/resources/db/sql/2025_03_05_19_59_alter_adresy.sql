--liquibase formatted sql
--changeset vkorolov:2025_03_05_19_59_alter_adresy.sql

alter table adresy
    add column if not exists lokal varchar(50);
