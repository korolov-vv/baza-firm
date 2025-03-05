--liquibase formatted sql
--changeset vkorolov:2025_02_28_23_30_alter_adresy.sql


alter table adresy
alter column kraj drop not null;
