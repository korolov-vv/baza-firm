--liquibase formatted sql
--changeset vkorolov:2025_03_05_21_26_alter_jednoosobowe_dzialalnosci_gospodarcze.sql

alter table jednoosobowe_dzialalnosci_gospodarcze
    alter column data_rozpoczecia type date using (NULLIF(data_rozpoczecia, '')::date);

alter table jednoosobowe_dzialalnosci_gospodarcze
    alter column data_zawieszenia type date using (NULLIF(data_zawieszenia, '')::date);

alter table jednoosobowe_dzialalnosci_gospodarcze
    alter column data_zakonczenia type date using (NULLIF(data_zakonczenia, '')::date);

alter table jednoosobowe_dzialalnosci_gospodarcze
    alter column data_wykreslenia type date using (NULLIF(data_wykreslenia, '')::date);

alter table jednoosobowe_dzialalnosci_gospodarcze
    alter column data_wznowienia type date using (NULLIF(data_wznowienia, '')::date);

