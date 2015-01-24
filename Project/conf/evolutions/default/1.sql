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
  msg                       varchar(255),
  constraint pk_message primary key (id))
;

create table message_box (
  id                        bigint not null,
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
  location_id               bigint,
  msg_box_id                bigint,
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
alter table user add constraint fk_user_location_3 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_user_location_3 on user (location_id);
alter table user add constraint fk_user_msgBox_4 foreign key (msg_box_id) references message_box (id) on delete restrict on update restrict;
create index ix_user_msgBox_4 on user (msg_box_id);



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

