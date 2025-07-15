--liquibase formatted sql
--changeset vkorolov:2025_07_15_21_11_podmioty_gospodarcze_regon_migrate.sql


update podmioty_gospodarcze p
set regon = (select o.regon
             from podmioty_gospodarcze p1
             join osoby o on p1.wlasciciel_id = o.id
             where p.id = p1.id)
