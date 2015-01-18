# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table lift (
  id                        bigint not null,
  name                      varchar(255),
  type                      varchar(255),
  max_persons               integer,
  seat_heating              boolean,
  weather_protection        varchar(255),
  constraint pk_lift primary key (id))
;

create table user (
  id                        bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  age                       integer,
  gender                    varchar(255),
  constraint pk_user primary key (id))
;

create sequence lift_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists lift;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists lift_seq;

drop sequence if exists user_seq;

