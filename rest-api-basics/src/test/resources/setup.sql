drop table if exists gift_certificate_tag;
drop table if exists gift_certificate;
drop table if exists tag;

create table gift_certificate(
  id bigint not null auto_increment primary key,
  name nvarchar(255) not null,
  description text not null,
  price decimal(10, 2) not null,
  duration integer not null,
  create_date nvarchar(23) not null,
  last_update_date nvarchar(23) not null
);

create table tag(
  id BIGINT auto_increment primary key,
  name nvarchar(255) not null
);

create table gift_certificate_tag(
  id bigint not null auto_increment primary key,
  gift_certificate_id bigint not null,
  tag_id bigint not null,
  UNIQUE (gift_certificate_id, tag_id),
  foreign key (gift_certificate_id)
      references gift_certificate(id) ON DELETE CASCADE,
  foreign key (tag_id)
      references tag(id) ON DELETE CASCADE
);