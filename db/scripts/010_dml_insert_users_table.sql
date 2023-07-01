insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$GxKdn0geFQ87W6W413ttieSxR3rSTDon3Yn9zOiR9scWRNJQOtK2C',
(select id from authorities where authority = 'ROLE_ADMIN'));