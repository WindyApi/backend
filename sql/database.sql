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

drop table if exists interface_info;
create table interface_info
(
    id             bigint auto_increment comment 'id' primary key,
    `name`         varchar(32)                          not null unique comment '接口名称',
    `describe`     varchar(256)                         not null comment '接口描述',
    method         varchar(16)                          not null comment '请求类型',
    url            varchar(256)                         not null comment '接口地址',
    params         text                                 null comment '请求参数',
    requestHeader  text                                 null comment '请求头',
    responseHeader text                                 null comment '响应头',
    `status`       tinyint(1) default 0                 not null comment '接口状态 0 下线 1 在线',
    userId         bigint                               not null comment '创建人id',
    createTime     datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint    default 0                 not null comment '是否删除'
)
    comment '接口信息' charset utf8;

drop table if exists user_interface_record;
create table if not exists user_interface_record
(
    `id`              bigint                               not null auto_increment comment '主键' primary key,
    `userId`          bigint                               not null comment '调用用户 id',
    `interfaceId` bigint                               not null comment '接口 id',
    `totalNum`        int        default 0                 not null comment '总调用次数',
    `leftNum`         int        default 0                 not null comment '剩余调用次数',
    `status`          tinyint(1) default 0                 not null comment '0-正常，1-禁用',
    `createTime`      datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`      datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`        tinyint    default 0                 not null comment '是否删除(0-未删, 1-已删)'
)
    comment '用户调用接口记录' charset utf8;

drop table if exists user_subscribe_record;
create table user_subscribe_record
(
    `id`              bigint                             not null auto_increment comment '主键' primary key,
    `userId`          bigint                             not null comment '调用用户 id',
    `interfaceId` bigint                             not null comment '接口 id',
    `increase`        int                                not null comment '增加的次数',
    `price`           int                                not null comment '价格',
    `createTime`      datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户订阅记录' charset utf8;