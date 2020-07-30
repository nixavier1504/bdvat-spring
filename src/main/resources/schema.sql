/*
CREATE TABLE config_json_new
(
 username varchar(100) NOT NULL,
 profile varchar(11) NOT NULL ,
 config_1 varchar(100) DEFAULT NULL,
 config_2 varchar(100) DEFAULT NULL
);
*/


CREATE SEQUENCE if not exists unique_id_seq;

drop table if exists config_json_x;
create table if not exists config_json_x 
(
id INTEGER DEFAULT NEXTVAL('unique_id_seq'),
username text NOT NULL, 
source text NOT NULL,
dataset text DEFAULT NULL,
kerb text DEFAULT NULL
);


--drop table if exists user_pro;
create table if not exists user_pro 
(
username text,
session_id bigint
);

--drop table if exists user_py_pro;
create table if not exists user_py_pro 
(
username text,
session_id bigint
);
