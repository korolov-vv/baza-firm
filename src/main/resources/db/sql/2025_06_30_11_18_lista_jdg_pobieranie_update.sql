--liquibase formatted sql
--changeset vkorolov:2025_06_30_19_20_jednoosobowe_dzialalnosci_gospodarcze_rename_update.sql


alter table lista_jdg_pobieranie
add column nieobsluzone_linki varchar(10);
