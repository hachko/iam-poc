insert into users (username, password, email) values
('john_doe', 'password123', 'john_doe@example.com'),
('jane_smith', 'securepass', 'jane_smith@example.com');

insert into roles (role_name) values
('USER'),
('ADMIN');

insert into user_role (user_id, role_id) values
(1, 1),
(2, 1),
(2, 2);