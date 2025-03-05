--liquibase formatted sql
--changeset vkorolov:2025_03_03_15_35_obywatelstwa_init.sql


create table obywatelstwa (
                              osoba_id bigint not null,
                              kraj_id bigint not null,
                              primary key (osoba_id, kraj_id),
                              foreign key (osoba_id) references osoby(id) on delete cascade,
                              foreign key (kraj_id) references kraje(id) on delete cascade
);
