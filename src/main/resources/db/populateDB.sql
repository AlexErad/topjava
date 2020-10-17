DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
from meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (description, calories, datetime, user_id)
VALUES ('Breakfast', 400, '2020-01-31 09:00', 100000),
       ('Dinner', 500, '2020-01-31 13:00', 100000),
       ('Admin_Dinner', 500, '2020-01-31 13:00', 100001)