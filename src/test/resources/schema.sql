DROP TABLE IF EXISTS product;

create table product
(
  id          varchar(255) not null primary key,
  description varchar(255) null,
  name        varchar(255) null,
  price       double       not null
);