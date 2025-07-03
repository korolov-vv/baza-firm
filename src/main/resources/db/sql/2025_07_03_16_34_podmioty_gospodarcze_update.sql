--liquibase formatted sql
--changeset vkorolov:2025_07_03_16_34_podmioty_gospodarcze_update.sql


alter table podmioty_gospodarcze
    alter column nazwa type varchar;
