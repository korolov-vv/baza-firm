--liquibase formatted sql
--changeset vkorolov:2025_02_27_22_30_alter_lista_jdg_pobieranie.sql


alter table lista_jdg_pobieranie
add column if not exists czy_stare_dane boolean;

update lista_jdg_pobieranie
set czy_stare_dane = true;

alter table jednoosobowe_dzialalnosci_gospodarcze
drop column if exists czy_pobrano_szczegoly;
