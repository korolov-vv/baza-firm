--liquibase formatted sql
--changeset vkorolov:2025_05_13_22_24_alter_jdg

alter table jednoosobowe_dzialalnosci_gospodarcze
add column if not exists rok_pkd varchar(4);

alter table jednoosobowe_dzialalnosci_gospodarcze
    add column if not exists version INT;

alter table jednoosobowe_dzialalnosci_gospodarcze
    add column if not exists last_modified_date timestamp;

update jednoosobowe_dzialalnosci_gospodarcze
set rok_pkd = 2025
where data_rozpoczecia >= '2025-01-01';

update jednoosobowe_dzialalnosci_gospodarcze
set rok_pkd = 2007
where data_rozpoczecia < '2025-01-01';

update jednoosobowe_dzialalnosci_gospodarcze
set version = 1;
