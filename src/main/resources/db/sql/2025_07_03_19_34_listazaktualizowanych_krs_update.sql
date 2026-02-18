--liquibase formatted sql
--changeset vkorolov:2025_07_03_19_34_listazaktualizowanych_krs_update.sql


alter table lista_zaktualizowanych_krs
    add column if not exists status_pobierania varchar(50);
