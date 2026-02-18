--liquibase formatted sql
--changeset vkorolov:2025_07_03_21_12_podmioty_gospodarcze_update.sql


alter table podmioty_gospodarcze
    add column if not exists reprezentacja jsonb;

alter table podmioty_gospodarcze
    add column if not exists wspolnicy_spzoo jsonb;
