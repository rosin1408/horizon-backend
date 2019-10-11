create table users (
	id serial primary key not null,
	name varchar not null,
	username varchar not null,
  email varchar not null,
  password varchar not null,
  blocked boolean default true
);

create table roles (
	id serial primary key not null,
	name varchar not null
);

create table user_roles (
	id serial primary key not null,
	user_id bigint references users (id) not null,
	role_id bigint references roles (id) not null
);

create table users_tokens (
	id serial primary key not null,
	user_id bigint references users (id) not null,
	uuid varchar not null,
  token_type varchar not null,
  valid_at timestamp not null
);

insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');