create table article_comment
(
    id         bigint auto_increment
        primary key,
    article_id bigint   not null,
    comment    tinytext not null
);

create index article_index
    on article_comment (article_id);

create table article_content
(
    article_id bigint not null
        primary key,
    content    text   not null
);

create table article_meta
(
    id           int auto_increment
        primary key,
    author_id    int unsigned             null,
    author_img   varchar(500)             not null,
    author_name  varchar(24)              not null,
    title        varchar(255)             not null,
    img          varchar(500)             null,
    summary      text                     null,
    link         varchar(500)             null,
    score        int unsigned default '0' null,
    views        int unsigned default '0' null,
    votes        int unsigned default '0' null,
    comments     int unsigned default '0' null comment '评论量',
    collects     int unsigned default '0' null comment '收藏量',
    shares       int unsigned default '0' null comment '分享量',
    publish_date datetime                 null,
    edit_date    datetime                 null,
    is_published tinyint(1)   default 0   null
);

create table user
(
    id                  int auto_increment
        primary key,
    username            varchar(25)          not null comment '手机号或邮箱号，后期统一改成手机号，或自动生成',
    password            varchar(128)         not null comment '密码',
    email               varchar(20)          null,
    authorities         int        default 0 not null comment '权限位（默认为0：访客）',
    enabled             tinyint(1) default 1 not null comment '账号状态（默认为true：正常状态）',
    account_non_locked  tinyint(1) default 1 not null comment '账户锁定（true：未锁定）',
    account_non_expired tinyint(1) default 1 not null comment '账号过期（true：未过期）',
    constraint user_username_uindex
        unique (username)
);

create table user_article
(
    user_id    int unsigned not null,
    article_id int unsigned not null,
    primary key (user_id, article_id)
)
    comment '用户-文章关系表1:n';

create table user_info
(
    id          int                                not null comment '用户全局唯一ID'
        primary key,
    username    varchar(20)                        not null comment '用户展示名',
    gender      tinyint  default 0                 null comment '用户性别：0-未知，1-男，2-女',
    email       varchar(50)                        null comment '邮箱',
    status      tinyint  default 0                 null comment '账号状态：0-未激活，1-活跃，2-禁用',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint username
        unique (username)
)
    comment '用户基础信息';

create table user_summary
(
    id       int          not null comment '用户唯一ID',
    nickname varchar(25)  not null comment '用户展示名',
    avatar   varchar(255) null comment '用户头像URL',
    constraint id
        unique (id),
    constraint name
        unique (nickname)
)
    comment '用户摘要信息表';

create index id_2
    on user_summary (id)
    comment '为id字段创建索引';

