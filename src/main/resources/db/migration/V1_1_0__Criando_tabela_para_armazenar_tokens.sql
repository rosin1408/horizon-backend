create table users_tokens (
	id serial primary key not null,
	user_id bigint references users (id) not null,
	uuid varchar not null,
  token_type varchar not null
);