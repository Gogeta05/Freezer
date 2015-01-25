# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table interests (
  id                        bigint not null,
  FK_OWNER                  bigint,
  FK_PARENT                 bigint,
  title                     varchar(255),
  interest_on               boolean,
  constraint pk_interests primary key (id))
;

create table lift (
  id                        bigint not null,
  name                      varchar(255),
  location_postal           integer,
  type                      varchar(255),
  max_persons               integer,
  seat_heating              boolean,
  weather_protection        varchar(255),
  constraint pk_lift primary key (id))
;

create table location (
  id                        bigint not null,
  plz                       integer,
  municipality              varchar(255),
  constraint pk_location primary key (id))
;

create table message (
  id                        bigint not null,
  FK_BOX                    bigint,
  FK_FROM                   bigint,
  FK_TO                     bigint,
  msg                       varchar(255),
  constraint pk_message primary key (id))
;

create table message_box (
  id                        bigint not null,
  FK_OWNER                  bigint,
  constraint pk_message_box primary key (id))
;

create table user (
  id                        bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  age                       integer,
  gender                    varchar(255),
  location_plz              integer,
  lift_name                 varchar(255),
  constraint pk_user primary key (id))
;

create sequence interests_seq;

create sequence lift_seq;

create sequence location_seq;

create sequence message_seq;

create sequence message_box_seq;

create sequence user_seq;

alter table interests add constraint fk_interests_owner_1 foreign key (FK_OWNER) references user (id) on delete restrict on update restrict;
create index ix_interests_owner_1 on interests (FK_OWNER);
alter table interests add constraint fk_interests_parent_2 foreign key (FK_PARENT) references interests (id) on delete restrict on update restrict;
create index ix_interests_parent_2 on interests (FK_PARENT);
alter table message add constraint fk_message_box_3 foreign key (FK_BOX) references message_box (id) on delete restrict on update restrict;
create index ix_message_box_3 on message (FK_BOX);
alter table message add constraint fk_message_from_4 foreign key (FK_FROM) references user (id) on delete restrict on update restrict;
create index ix_message_from_4 on message (FK_FROM);
alter table message add constraint fk_message_to_5 foreign key (FK_TO) references user (id) on delete restrict on update restrict;
create index ix_message_to_5 on message (FK_TO);
alter table message_box add constraint fk_message_box_owner_6 foreign key (FK_OWNER) references user (id) on delete restrict on update restrict;
create index ix_message_box_owner_6 on message_box (FK_OWNER);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists interests;

drop table if exists lift;

drop table if exists location;

drop table if exists message;

drop table if exists message_box;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists interests_seq;

drop sequence if exists lift_seq;

drop sequence if exists location_seq;

drop sequence if exists message_seq;

drop sequence if exists message_box_seq;

drop sequence if exists user_seq;

