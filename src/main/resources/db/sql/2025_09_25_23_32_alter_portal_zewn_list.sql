--liquibase formatted sql
--changeset vkorolov:2025_09_25_23_32_alter_portal_zewn_list.sql


alter table portal_zewn_list
    add column if not exists blad text;
