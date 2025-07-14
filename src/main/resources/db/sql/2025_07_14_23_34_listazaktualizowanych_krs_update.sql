--liquibase formatted sql
--changeset vkorolov:2025_07_14_23_34_listazaktualizowanych_krs_update.sql


alter table lista_zaktualizowanych_krs
    drop column if exists czy_obsluzona;
