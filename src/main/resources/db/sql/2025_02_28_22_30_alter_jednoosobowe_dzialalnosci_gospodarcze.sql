--liquibase formatted sql
--changeset vkorolov:2025_02_28_22_30_alter_jednoosobowe_dzialalnosci_gospodarcze.sql


alter table jednoosobowe_dzialalnosci_gospodarcze
    drop column if exists pkd_glowny;

alter table jednoosobowe_dzialalnosci_gospodarcze
    add column if not exists pkd_glowny_id bigint;

alter table jednoosobowe_dzialalnosci_gospodarcze
    add constraint FK_pkd_glowny_id foreign key (pkd_glowny_id) REFERENCES kody_pkd;





