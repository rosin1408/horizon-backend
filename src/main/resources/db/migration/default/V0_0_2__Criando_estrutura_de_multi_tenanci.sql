create table clients (
	id serial primary key not null,
	name varchar not null,
	tenant varchar not null
);

alter table users add column client_id bigint references clients (id) not null;