insert into users (username, password, email) values
-- default passwords : password123 and securepass
('john_doe', '$2a$10$bzahDRC.e/Dlx3h7.iUYO.pz23I3bn8KKDSObY5yBOEvXV.CI7gK.', 'john_doe@example.com'),
('jane_smith', '$2a$10$Ky4VB61kVWZ5UH8HeRUIZ.gmwPrZTD2Rx.StdQvhcSiCyLnExxT6S', 'jane_smith@example.com');

insert into roles (role_name) values
('USER'),
('ADMIN');

insert into user_role (user_id, role_id) values
(1, 1),
(2, 1),
(2, 2);