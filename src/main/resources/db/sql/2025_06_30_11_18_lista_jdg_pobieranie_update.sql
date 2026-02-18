--liquibase formatted sql
--changeset vkorolov:2025_06_30_11_18_lista_jdg_pobieranie_update.sql


alter table lista_jdg_pobieranie
add column if not exists nieobsluzone_linki jsonb;
