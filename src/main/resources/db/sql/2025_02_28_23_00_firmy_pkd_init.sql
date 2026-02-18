--liquibase formatted sql
--changeset vkorolov:2025_02_28_23_00_firmy_pkd_init.sql


create table firmy_pkd (
               firma_id bigint not null,
               pkd_id bigint not null,
               primary key (firma_id, pkd_id),
               foreign key (firma_id) references jednoosobowe_dzialalnosci_gospodarcze(id) on delete cascade,
               foreign key (pkd_id) references kody_pkd(id) on delete cascade
);





