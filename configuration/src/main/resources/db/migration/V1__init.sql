CREATE SEQUENCE user_seq INCREMENT 1;

CREATE TABLE public.user
(
    id          BIGINT  PRIMARY KEY NOT NULL DEFAULT nextval('user_seq'),
    first_name  TEXT,
    last_name   TEXT,
    username    TEXT                NOT NULL,
    email       TEXT                NOT NULL,
    password    TEXT                NOT NULL
)
