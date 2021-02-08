--liquibase formatted sql
--changeset sysoiev:2
-- Insert data

-- populate users
INSERT INTO users (username, password, phone_number, created, updated, status)
VALUES ('admin', '$2y$12$p2X0OkKfybvIspoee/x93eAcv6TVqRyQOApZVYKZxrMQIegsBIEYG', '+711111111',
        CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate roles
INSERT INTO roles
VALUES (1, 'ROLE_USER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO roles
VALUES (2, 'ROLE_MODERATOR', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO roles
VALUES (3, 'ROLE_ADMIN', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate user_roles
INSERT INTO user_roles
VALUES (1, 1);
INSERT INTO user_roles
VALUES (1, 2);
INSERT INTO user_roles
VALUES (1, 3);

-- populate developers
INSERT INTO developers
VALUES (1, 'Alex', 'Andreev', 100000, '1986-04-11', '2019-11-11', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO developers
VALUES (2, 'Vlad', 'Andreev', 150000, '1963-05-09', '2018-10-11', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate skills
INSERT INTO skills
VALUES (1, 'Java', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO skills
VALUES (2, 'C++', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate developers_skills
INSERT INTO developers_skills
VALUES (1, 1);
INSERT INTO developers_skills
VALUES (2, 1);
INSERT INTO developers_skills
VALUES (2, 2);