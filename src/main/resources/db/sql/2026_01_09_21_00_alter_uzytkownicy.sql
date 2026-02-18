--liquibase formatted sql
--changeset vkorolov:2026_01_09_21_00_alter_uzytkownicy.sql

alter table uzytkownicy
    add column if not exists czy_email_potwierdzony boolean;
