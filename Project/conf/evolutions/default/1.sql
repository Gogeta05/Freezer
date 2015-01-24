# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table interests (
  id                        bigint not null,
  title                     varchar(255) not null,
  interest_on               boolean)
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
  msg_box_id                bigint,
  constraint pk_user primary key (id))
;

create sequence interests_seq;

create sequence lift_seq;

create sequence message_seq;

create sequence message_box_seq;

create sequence user_seq;

alter table user add constraint fk_user_msgBox_1 foreign key (msg_box_id) references message_box (id) on delete restrict on update restrict;
create index ix_user_msgBox_1 on user (msg_box_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists interests;

drop table if exists lift;

drop table if exists message;

drop table if exists message_box;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists interests_seq;

drop sequence if exists lift_seq;

drop sequence if exists message_seq;

drop sequence if exists message_box_seq;

drop sequence if exists user_seq;

