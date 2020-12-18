CREATE SEQUENCE user_seq INCREMENT 50;

CREATE TABLE public.user
(
    id          BIGINT  PRIMARY KEY NOT NULL DEFAULT nextval('user_seq'),
    first_name  TEXT,
    last_name   TEXT,
    username    TEXT                NOT NULL UNIQUE,
    email       TEXT                NOT NULL UNIQUE,
    password    TEXT                NOT NULL
)
