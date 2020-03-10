create table commercial_places (
  id serial not null primary key,
  name varchar not null,
  opening_time time not null,
  closing_time time not null
)