drop database if exists windy_api;
create database windy_api;

use windy_api;

drop table if exists user;
create table user
(
    id         bigint auto_increment comment 'id'
        primary key,
    nickname   varchar(256)                           null comment '用户昵称',
    account    varchar(256)                           not null comment '账号',
    avatar     varchar(1024)                          null comment '用户头像',
    gender     tinyint                                null comment '性别',
    role       varchar(256) default 'user'            not null comment '用户角色：user / admin',
    password   varchar(512)                           not null comment '密码',
    accessKey  varchar(512)                           not null comment 'accessKey',
    secretKey  varchar(512)                           not null comment 'secretKey',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (account)
)
    comment '用户' charset utf8;