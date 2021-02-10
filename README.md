# developers_db

[![Build Status](https://travis-ci.com/Bada1208/developers_db.svg?branch=master)](https://travis-ci.com/Bada1208/developers_db)

JWT based spring security REST Api with DB & CRUD operations with such entities:

User (id, username, password, created, updated, lastPasswordChangeDate, Status status)

Skill (id, name)

Developer (id, firstName, lastName, Set skills, Account account)

enum Status {... ACTIVE, DELETED, BANNED ...}

Requirements:

All CRUD operations for every entity

MVC pattern

Use Maven & Spring (IoC, Security, Data, etc.)

For connection with DB use - Spring Data

Initializing DB should be with liquibase

User interaction needs to be implemented with Postman (https://www.getpostman.com/)

Repository should have badge travis(https://travis-ci.com/)

App should have 3 roles: ROLE_ADMIN (has full access to all entities)

ROLE_MODERATOR (has read and write access for all entities Developer)

ROLE_USER (has read access for entities Developer,Skill)

Technologies: Java, MySQL, Spring (MVC, Web, Data, Security, Boot), Lombok, Maven, Liquibase.
