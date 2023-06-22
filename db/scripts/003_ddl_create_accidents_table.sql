create table if not exists accidents
(
    id serial primary key,
    name varchar not null,
    text text not null,
    address varchar not null,
    type_id int references accident_types (id) not null
);