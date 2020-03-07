create table services (
  id serial not null primary key,
  name varchar not null,
  description varchar,
  duration SMALLINT not null
)