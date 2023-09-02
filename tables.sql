create table course
(
    cid        varchar(20) not null
        primary key,
    cname      varchar(20) null,
    teacher    varchar(20) null,
    capacity   varchar(20) null,
    selected   varchar(20) null,
    timelength varchar(20) null,
    place      varchar(20) null
);

create table user
(
    uid       varchar(20) not null
        primary key,
    username  varchar(20) null,
    password  varchar(20) null,
    uname     varchar(20) null,
    gender    varchar(20) null,
    phone     varchar(20) null,
    authority int         null,
    constraint uid
        unique (uid)
);

create table courseselection
(
    id  int auto_increment
        primary key,
    uid varchar(20) null,
    cid varchar(20) null,
    constraint courseselection_ibfk_1
        foreign key (uid) references user (uid),
    constraint courseselection_ibfk_2
        foreign key (cid) references course (cid)
);

create index cid
    on courseselection (cid);

create index uid
    on courseselection (uid);

