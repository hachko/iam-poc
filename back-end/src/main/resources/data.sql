insert into users (id, username, password, email) values
(1, 'john_doe', 'password123', 'john_doe@example.com'),
(2, 'jane_smith', 'securepass', 'jane_smith@example.com');

insert into roles (id, role_name) values
(1, 'USER'),
(2, 'ADMIN');

insert into user_roles (user_id, role_id) values
(1, 1),
(2, 1),
(2, 2);