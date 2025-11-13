DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE users (
  id          INT PRIMARY KEY auto_increment,
  username    VARCHAR(20) NOT NULL,
  password    VARCHAR(200) NOT NULL,
  email       VARCHAR(50) NOT NULL,
  UNIQUE (username), UNIQUE (email)
);

CREATE TABLE roles (
    id INT PRIMARY KEY auto_increment,
    role_name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);