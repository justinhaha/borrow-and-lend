drop database if exists trade;
create database trade;
use trade;
-- 用户表
drop table if exists users;
create table users(
userid         	int            primary key    auto_increment comment '用户id',
username        varchar(32)    unique not null comment  '用户名',
password        varchar(32)    not null      comment   '用户密码',
email           varchar(64)                  comment   '用户邮箱',
school          varchar(64)    not null      comment    '用户所在学校',
court           varchar(64)          comment    '用户所在院',
professional    varchar(64)        comment    '用户所学专业'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 商品表
drop table if exists shop;
create table shop(
shopid      int  primary key  auto_increment comment  '商品id',
shopname    varchar(64)   not null      comment  '商品名',
description varchar(1024)               comment  '商品描述',
username    varchar(64)   not null      comment  '发布者姓名',
userphone   varchar(32)    not null      comment   '用户电话',
category    varchar(64)   not null      comment   '商品类别',
picture     varchar(1024)                 comment  '商品图片',
price       varchar(64)        not null      comment   '商品价格',
put_time    timestamp     not null      comment   '发布时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 留言 表
drop table if exists message;
create table message(
messageid     int            primary key auto_increment  comment'id',
content       varchar(1024)  not null    comment '留言内容',
username      varchar(1024)  not null    comment '留言者姓名',   
receivename   varchar(32)    not null    comment '接收留言者姓名',
leave_time    timestamp      not null    comment   '留言时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 收藏表
drop table if exists collect;
create table collect(
collectionid  int            primary key auto_increment  comment'id',
shopid        int  not null    comment '商品id',
username      varchar(1024)  not null    comment '收藏者姓名'  
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 求购信息表
drop table if exists look;
create table look(
lookid        int            primary key auto_increment  comment'id',
lookname    varchar(64)   not null      comment  '求购商品名',
description varchar(1024)               comment  '求购描述',
username    varchar(64)   not null      comment  '求购者姓名',
userphone   varchar(32)    not null      comment   '求购者电话',
category    varchar(64)   not null      comment   '求购类别',
put_time    timestamp     not null      comment   '发布时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
