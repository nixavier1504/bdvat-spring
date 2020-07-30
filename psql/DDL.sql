drop table if exists config_json;
create table if not config_json (
id bigint,
config_1 char(200),
config_2 char(200),
config_3 char(200)
);

--insert into config_json (id,config_1, config_2, config_3) values (123, 'meta1', 'meta2','meta3');