--liquibase formatted sql
--changeset vkorolov:2025_05_17_11_00_alter_jdg.sql

alter table jednoosobowe_dzialalnosci_gospodarcze
    alter column ceidg_id drop not null;
