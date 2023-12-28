create schema if not exists rest_api_security;

drop table if exists users_roles;
drop table if exists user_purchase_data;
drop table if exists purchase_data_gift_certificate;
drop table if exists purchase_data;
drop table if exists user_orders;
drop table if exists order_gift_certificate;
drop table if exists gift_certificate_tag;
drop table if exists roles;
drop table if exists user;
drop table if exists orders;
drop table if exists gift_certificate;
drop table if exists tag;

drop table if exists users_roles_audit;
drop table if exists user_purchase_data_audit;
drop table if exists purchase_data_gift_certificate_audit;
drop table if exists purchase_data_audit;
drop table if exists user_orders_audit;
drop table if exists order_gift_certificate_audit;
drop table if exists gift_certificate_tag_audit;
drop table if exists roles_audit;
drop table if exists user_audit;
drop table if exists orders_audit;
drop table if exists gift_certificate_audit;
drop table if exists tag_audit;
drop table if exists REVINFO;

create table gift_certificate
(
    id               bigint         not null auto_increment primary key,
    name             nvarchar(255)  not null unique,
    description      text           not null,
    price            decimal(10, 2) not null,
    duration         integer        not null,
    create_date      nvarchar(23)   not null,
    last_update_date nvarchar(23)   not null
);

create table tag
(
    id   BIGINT        not null auto_increment primary key,
    name nvarchar(255) not null unique
);

create table user
(
    id       bigint        not null auto_increment primary key,
    username nvarchar(255) not null unique,
    password nvarchar(255) not null
);

create table roles
(
    id   BIGINT       not null auto_increment primary key,
    name varchar(255) not null unique
);

create table orders
(
    id            bigint         not null auto_increment primary key,
    cost          decimal(10, 2) not null,
    purchase_date datetime(3)    not null
);

create table purchase_data
(
    id     bigint                     not null auto_increment primary key,
    start  datetime(6)                not null,
    end    datetime(6)                not null,
    status enum ('ACTIVE', 'EXPIRED') not null
);

create table gift_certificate_tag
(
    gift_certificate_id bigint not null,
    tag_id              bigint not null,
    UNIQUE KEY `uniq_id1` (`gift_certificate_id`, `tag_id`),
    foreign key (gift_certificate_id) references gift_certificate (id) ON DELETE CASCADE,
    foreign key (tag_id) references tag (id) ON DELETE CASCADE
);

create table order_gift_certificate
(
    order_id            bigint not null,
    gift_certificate_id bigint not null,
    KEY `uniq_id2` (`order_id`, `gift_certificate_id`),
    foreign key (order_id) references orders (id) ON DELETE CASCADE,
    foreign key (gift_certificate_id) references gift_certificate (id) ON DELETE CASCADE
);

create table purchase_data_gift_certificate
(
    purchase_data_id    bigint not null,
    gift_certificate_id bigint,
    KEY `uniq_id3` (`purchase_data_id`, `gift_certificate_id`),
    foreign key (purchase_data_id) references purchase_data (id) ON DELETE CASCADE,
    foreign key (gift_certificate_id) references gift_certificate (id) ON DELETE CASCADE
);

create table user_purchase_data
(
    user_id          bigint,
    purchase_data_id bigint not null,
    primary key (purchase_data_id),
    UNIQUE KEY `uniq_id4` (`user_id`, `purchase_data_id`),
    foreign key (user_id) references user (id) ON DELETE CASCADE,
    foreign key (purchase_data_id) references purchase_data (id) ON DELETE CASCADE
);

create table user_orders
(
    user_id  bigint,
    order_id bigint not null,
    UNIQUE KEY `uniq_id5` (`user_id`, `order_id`),
    foreign key (user_id) references user (id) ON DELETE CASCADE,
    foreign key (order_id) references orders (id) ON DELETE CASCADE
);

create table if not exists users_roles
(
    id      BIGINT not null auto_increment primary key,
    user_id BIGINT not null,
    role_id BIGINT not null,
    UNIQUE KEY `unique_id6` (`user_id`, `role_id`),
    foreign key (user_id) references user (id) ON DELETE CASCADE,
    foreign key (role_id) references roles (id) ON DELETE CASCADE
);

create table REVINFO
(
    REV      integer not null auto_increment,
    REVTSTMP bigint,
    primary key (REV)
);

create table gift_certificate_audit
(
    id               bigint  not null,
    REV              integer not null,
    REVTYPE          tinyint,
    name             varchar(255),
    description      varchar(255),
    price            decimal(10, 2),
    duration         integer,
    create_date      varchar(255),
    last_update_date varchar(255),
    primary key (id, REV)
);

create table tag_audit
(
    id      bigint  not null,
    REV     integer not null,
    REVTYPE tinyint,
    name    varchar(255),
    primary key (id, REV)
);

create table roles_audit
(
    id      bigint  not null,
    REV     integer not null,
    REVTYPE tinyint,
    name    varchar(255),
    primary key (id, REV)
);

create table user_audit
(
    id       bigint  not null,
    REV      integer not null,
    REVTYPE  tinyint,
    username varchar(255),
    password varchar(255),
    primary key (id, REV)
);

create table orders_audit
(
    id            bigint  not null,
    REV           integer not null,
    REVTYPE       tinyint,
    cost          decimal(10, 2),
    purchase_date datetime(6),
    primary key (id, REV)
);

create table purchase_data_audit
(
    id      bigint  not null,
    REV     integer not null,
    REVTYPE tinyint,
    start   datetime(6),
    end     datetime(6),
    status  varchar(255),
    primary key (id, REV)
);

create table gift_certificate_tag_audit
(
    REV                 integer not null,
    REVTYPE             tinyint,
    gift_certificate_id bigint  not null,
    tag_id              bigint  not null,
    primary key (REV, gift_certificate_id, tag_id)
);

create table order_gift_certificate_audit
(
    REV                 integer not null,
    REVTYPE             tinyint,
    order_id            bigint  not null,
    gift_certificate_id bigint  not null,
    primary key (REV, order_id, gift_certificate_id)
);

create table purchase_data_gift_certificate_audit
(
    REV                 integer not null,
    purchase_data_id    bigint  not null,
    gift_certificate_id bigint,
    primary key (purchase_data_id, REV)
);

create table user_orders_audit
(
    REV      integer not null,
    user_id  bigint,
    order_id bigint  not null,
    primary key (order_id, REV)
);

create table user_purchase_data_audit
(
    REV              integer not null,
    user_id          bigint,
    purchase_data_id bigint  not null,
    primary key (purchase_data_id, REV)
);

create table users_roles_audit
(
    REV     integer not null,
    REVTYPE tinyint,
    user_id bigint  not null,
    role_id bigint  not null,
    primary key (REV, user_id, role_id)
);

alter table gift_certificate_audit
    add constraint FKfy4m0978oj2alpygcnew1yram foreign key (REV) references REVINFO (REV);
alter table gift_certificate_tag_audit
    add constraint FK3b7vs752iw72831gb7teyk5ur foreign key (REV) references REVINFO (REV);
alter table order_gift_certificate_audit
    add constraint FKa24yebqbp29ea440hh8t2laxy foreign key (REV) references REVINFO (REV);
alter table orders_audit
    add constraint FKr6ew36o5upul2kybqoeqfma6s foreign key (REV) references REVINFO (REV);
alter table purchase_data_audit
    add constraint FKn2oqhx7nycq73sceoas13qkqh foreign key (REV) references REVINFO (REV);
alter table purchase_data_gift_certificate_audit
    add constraint FK9jjb40ihj08wl2vbhvrihna2j foreign key (purchase_data_id, REV) references purchase_data_audit (id, REV);
alter table tag_audit
    add constraint FK7txw2uaahnghfhd56xwxhufx4 foreign key (REV) references REVINFO (REV);
alter table roles_audit
    add constraint FKs6uuqpw3tnabie1gf5ih8l2it foreign key (REV) references REVINFO (REV);
alter table user_audit
    add constraint FKt4lwhp6wkwxsfq5d36ur8p4lw foreign key (REV) references REVINFO (REV);
alter table user_orders_audit
    add constraint FKllyc39whbdypkgd59wxr5l4h9 foreign key (order_id, REV) references orders_audit (id, REV);
alter table user_purchase_data_audit
    add constraint FK72q2ct900nvmayvrm8mie10cc foreign key (purchase_data_id, REV) references purchase_data_audit (id, REV);
alter table users_roles_audit
    add constraint FK3ceo3kawvk6hswom83yymy75o foreign key (REV) references REVINFO (REV);