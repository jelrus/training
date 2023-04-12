drop schema if exists rest_api_basics;

drop table if exists gift_certificate_tag_binding;
drop table if exists gift_certificate;
drop table if exists tag;

create schema if not exists rest_api_basics;

create table rest_api_basics.gift_certificate(
    id bigint not null auto_increment primary key,
    name nvarchar(255) not null unique,
    description text not null,
    price decimal(10, 2) not null,
    duration integer not null,
    date_create datetime not null,
    date_last_update datetime not null
);
create table rest_api_basics.tag(
    id BIGINT auto_increment primary key,
    name nvarchar(255) not null unique
);
create table rest_api_basics.gift_certificate_tag(
    id bigint not null auto_increment primary key,
    gift_certificate_id bigint not null,
    tag_id bigint not null,
    UNIQUE KEY `uniq_id` (`gift_certificate_id`, `tag_id`),
    foreign key (gift_certificate_id) references gift_certificate(id) ON DELETE CASCADE,
    foreign key (tag_id) references tag(id) ON DELETE CASCADE
);