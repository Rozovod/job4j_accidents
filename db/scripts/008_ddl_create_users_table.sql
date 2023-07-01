create table if not exists users (
     id serial primary key,
     username VARCHAR(50) NOT NULL,
     password VARCHAR(100) NOT NULL,
     enabled boolean default true,
     authority_id int not null references authorities(id)
 );