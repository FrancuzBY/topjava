DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (dateTime, description, calories)
VALUES ('2021-01-01 00:00:00', 'Egg', 700),
       ('2021-01-01 11:00:00', 'Juice', 100),
       ('2021-01-01 13:00:00', 'Soup', 450),
       ('2021-01-01 16:00:00', 'Cake', 200),
       ('2021-01-01 19:00:00', 'Rise', 500),
       ('2021-01-02 09:00:00', 'Tost', 800),
       ('2021-01-02 11:00:00', 'Tea', 50),
       ('2021-01-02 13:00:00', 'Soup', 400),
       ('2021-01-02 16:00:00', 'Fruit', 150),
       ('2021-01-02 19:00:00', 'Potatos', 350),
       ('2021-01-03 09:00:00', 'Tost', 800),
       ('2021-01-03 11:00:00', 'Coffe', 100),
       ('2021-01-03 13:00:00', 'Soup', 450),
       ('2021-01-03 16:00:00', 'Apple', 50),
       ('2021-01-03 19:00:00', 'Salat', 370);
