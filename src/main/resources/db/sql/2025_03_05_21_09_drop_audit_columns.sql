--liquibase formatted sql
--changeset vkorolov:2025_03_05_21_09_drop_audit_columns.sql

alter table adresy
    drop column if exists create_date,
    drop column if exists created_by,
    drop column if exists last_modified_date,
    drop column if exists last_modified_by;


alter table kraje
    drop column if exists create_date,
    drop column if exists created_by,
    drop column if exists last_modified_date,
    drop column if exists last_modified_by;

alter table osoby
    drop column if exists create_date,
    drop column if exists created_by,
    drop column if exists last_modified_date,
    drop column if exists last_modified_by;

alter table kody_pkd
    drop column if exists create_date,
    drop column if exists created_by,
    drop column if exists last_modified_date,
    drop column if exists last_modified_by;

alter table lista_jdg_pobieranie
    drop column if exists created_by,
    drop column if exists last_modified_date,
    drop column if exists last_modified_by;

