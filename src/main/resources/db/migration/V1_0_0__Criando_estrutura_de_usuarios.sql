create table users (
	id serial primary key not null,
	name varchar not null,
	username varchar not null,
    email varchar not null,
    password varchar not null
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