CREATE TABLE role
(
    name character varying(255) PRIMARY KEY
);

CREATE TABLE room
(
    id   integer PRIMARY KEY,
    name character varying(255) NOT NULL UNIQUE
);

CREATE TABLE person
(
    id        integer PRIMARY KEY,
    login     character varying(255) NOT NULL UNIQUE,
    password  character varying(255) NOT NULL,
    role_name character varying(255) NOT NULL,
    FOREIGN KEY (role_name) REFERENCES role (name)
);

CREATE TABLE message
(
    id        bigint PRIMARY KEY,
    date      timestamp without time zone NOT NULL,
    message   character varying(255)      NOT NULL,
    person_id integer                     NOT NULL,
    room_id   integer                     NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (id),
    FOREIGN KEY (room_id) REFERENCES room (id)
);


CREATE TABLE person_room
(
    person_id integer NOT NULL,
    room_id   integer NOT NULL,
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (person_id) REFERENCES person (id)
);


CREATE SEQUENCE hibernate_sequence;