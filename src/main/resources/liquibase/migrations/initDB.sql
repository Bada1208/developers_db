--liquibase formatted sql
--changeset sysoiev:1

-- Table: users
CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50)  NOT NULL UNIQUE,
    created      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status       VARCHAR(25)  NOT NULL DEFAULT 'NOT_ACTIVE'
)
    ENGINE = InnoDB;

-- Table: roles
CREATE TABLE IF NOT EXISTS roles
(
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    created TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status  VARCHAR(25)  NOT NULL DEFAULT 'ACTIVE'
)
    ENGINE = InnoDB;

-- Table: developers
CREATE TABLE IF NOT EXISTS developers
(
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    salary          DECIMAL      NOT NULL,
    birth_date      DATETIME     NOT NULL,
    employment_date DATETIME     NOT NULL,
    created         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status          VARCHAR(25)  NOT NULL DEFAULT 'ACTIVE'
)
    ENGINE = InnoDB;

-- Table: skills
CREATE TABLE IF NOT EXISTS skills
(
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    created TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    status  VARCHAR(25)  NOT NULL DEFAULT 'ACTIVE'
)
    ENGINE = InnoDB;

-- Table for mapping User and roles: user_roles
CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,

    UNIQUE (user_id, role_id)
)
    ENGINE = InnoDB;

-- Table for mapping developers and skills: developers_skills
CREATE TABLE IF NOT EXISTS developers_skills
(
    developer_id   BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    FOREIGN KEY (developer_id) REFERENCES developers (id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE,

    UNIQUE (developer_id, skill_id)
)
    ENGINE = InnoDB;