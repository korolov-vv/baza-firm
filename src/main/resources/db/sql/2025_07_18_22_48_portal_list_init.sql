--liquibase formatted sql
--changeset vkorolov:2025_07_18_22_48_portal_list_init.sql


create table if not exists portal_zewn_list (
    id BIGINT
        constraint portal_list_pk
            primary key,
    create_date TIMESTAMP,
    firmy jsonb,
    page_link varchar,
    next_page_link varchar,
    is_completed boolean
);

create sequence if not exists portal_zewn_list_seq increment 1 start 1;

alter table portal_zewn_list
    drop column if exists is_completed;

alter table portal_zewn_list
    add column if not exists uuid uuid;

alter table portal_zewn_list
    add column if not exists status_pobierania varchar(255);

alter table portal_zewn_list
    add column if not exists niepobrane_firmy jsonb;

alter table portal_zewn_list
    add column if not exists created_by bigint;

alter table portal_zewn_list
    add column if not exists last_modified_date timestamp;

alter table portal_zewn_list
    add column if not exists last_modified_by bigint;

alter table portal_zewn_list
    add column if not exists version integer default 0;
