create table if not exists accidents_rules
(
    id serial primary key,
    accident_id int references accidents (id) not null,
    rule_id int references rules (id) not null
);