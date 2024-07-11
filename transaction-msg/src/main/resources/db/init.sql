-- 创建用户表
drop table if exists student;
create table if not exists student
(
    id   varchar(32)    not null primary key comment '用户id',
    name varchar(500)   not null comment '用户名',
    age  int            not null comment '年龄'
) comment '用户表';

-- 创建本地消息表
drop table if exists t_msg;
create table if not exists t_msg
(
    id              varchar(32) not null primary key comment '消息id',
    body_json       text        not null comment '消息体,json格式',
    status          smallint    not null default 0 comment '消息状态，0：待投递到mq，1：投递成功，2：投递失败',
    fail_msg        text comment 'status=2 时，记录消息投递失败的原因',
    fail_count      int         not null default 0 comment '已投递失败次数',
    send_retry      smallint    not null default 1 comment '投递MQ失败了，是否还需要重试？1：是，0：否',
    next_retry_time datetime comment '投递失败后，下次重试时间',
    create_time     datetime comment '创建时间',
    update_time     datetime comment '最近更新时间',
    key idx_status (status)
) comment '本地消息表'
