
create table if not exists thing(
  name varchar(2048) not null comment '文件名',
  path varchar(1024) not null comment '文件路径',
  depth int default 0 comment '文件路径深度',
  file_type varchar(12) not null comment '文件类型'
);

-- create database everything_cat;
--
-- use everything_cat;
--
-- create table if not exists thing(
--   name varchar(64) not null comment '文件名',
--   path varchar(1024) not null comment '文件路径',
--   depth int default 0 comment '文件路径深度',
--   file_type varchar(12) not null comment '文件类型'
-- );