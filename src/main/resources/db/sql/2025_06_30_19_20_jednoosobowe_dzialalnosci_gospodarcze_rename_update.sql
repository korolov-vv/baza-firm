--liquibase formatted sql
--changeset vkorolov:2025_06_30_19_20_jednoosobowe_dzialalnosci_gospodarcze_rename_update.sql


alter table jednoosobowe_dzialalnosci_gospodarcze
    rename to podmioty_gospodarcze;

alter sequence jednoosobowe_dzialalnosci_gospodarcze_seq
    rename to podmioty_gospodarcze_seq;

alter table podmioty_gospodarcze
add column nip varchar(10);

alter table podmioty_gospodarcze
    add column rejestr varchar(50);

alter table podmioty_gospodarcze
    add column regon varchar(14);

alter table podmioty_gospodarcze
    add column numer_krs varchar(10);


