--liquibase formatted sql
--changeset vkorolov:2025_09_15_18_52_alter_podmioty_gospodarcze.sql


alter table podmioty_gospodarcze
    rename column pelne_info to pelne_info_archive;

alter table podmioty_gospodarcze
    add column if not exists pelne_info jsonb;

alter table podmioty_gospodarcze
    add column if not exists pelne_info_spolka jsonb;
