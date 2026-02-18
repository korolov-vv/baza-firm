--liquibase formatted sql
--changeset vkorolov:2026_01_17_22_30_alter_firmy_crm_columns_size.sql

ALTER TABLE firmy_crm
    ALTER COLUMN status_kontaktu TYPE varchar(50);

ALTER TABLE firmy_crm
    ALTER COLUMN sposob_kontaktu TYPE varchar(50);
