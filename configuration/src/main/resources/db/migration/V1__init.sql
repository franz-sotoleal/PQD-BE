CREATE SEQUENCE user_seq INCREMENT 50;
CREATE SEQUENCE product_seq INCREMENT 50;
CREATE SEQUENCE sq_info_seq INCREMENT 50;
CREATE SEQUENCE jira_info_seq INCREMENT 50;
CREATE SEQUENCE jira_issue_seq INCREMENT 50;
CREATE SEQUENCE release_info_sq_seq INCREMENT 50;
CREATE SEQUENCE release_info_jira_sprint_seq INCREMENT 50;
CREATE SEQUENCE release_info_seq INCREMENT 50;
CREATE SEQUENCE user_product_claim_seq INCREMENT 50;

CREATE TABLE public.user
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('user_seq'),
    first_name      TEXT,
    last_name       TEXT,
    username        TEXT                    NOT NULL    UNIQUE,
    email           TEXT                    NOT NULL    UNIQUE,
    password        TEXT                    NOT NULL
);

CREATE TABLE public.sq_info
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('sq_info_seq'),
    base_url        TEXT                    NOT NULL,
    component_name  TEXT                    NOT NULL,
    token           TEXT
);

CREATE TABLE public.jira_info
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('jira_info_seq'),
    base_url        TEXT                    NOT NULL,
    board_id        BIGINT                  NOT NULL,
    user_email      TEXT                    NOT NULL,
    token           TEXT                    NOT NULL
);

CREATE TABLE public.product
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('product_seq'),
    name            TEXT                    NOT NULL,
    token           TEXT,
    sq_info_id      BIGINT                              REFERENCES public.sq_info(id),
    jira_info_id    BIGINT                              REFERENCES public.jira_info(id)
    -- add other tool references here with another changelist by modifying this table
);

CREATE TABLE public.user_product_claim
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('user_product_claim_seq'),
    user_id         BIGINT                  NOT NULL    REFERENCES public.user(id),
    product_id      BIGINT                  NOT NULL    REFERENCES public.product(id),
    claim_level     TEXT
);

CREATE TABLE public.release_info_sq
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('release_info_sq_seq'),
    sec_rating      DECIMAL,
    rel_rating      DECIMAL,
    maint_rating    DECIMAL,
    sec_vulns       BIGINT,
    rel_bugs        BIGINT,
    maint_debt      BIGINT,
    maint_smells    BIGINT
);

CREATE TABLE public.release_info
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('release_info_seq'),
    product_id      BIGINT                  NOT NULL    REFERENCES public.product(id),
    created         TIMESTAMP   WITHOUT     TIME ZONE   DEFAULT Now(),
    quality_level   DECIMAL,
    release_info_sq_id  BIGINT                          REFERENCES public.release_info_sq(id)
    -- add other tool references here by modifying the table if you add support for another tool (or in case of a list,
    -- add the release info reference to the other table)
);

CREATE TABLE public.release_info_jira_sprint
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('release_info_jira_sprint_seq'),
    release_info_id BIGINT                              REFERENCES public.release_info(id),
    sprint_id       BIGINT,
    board_id        BIGINT,
    name            TEXT,
    start_time      TIMESTAMP,
    end_time        TIMESTAMP,
    goal            TEXT,
    browser_url     TEXT
);

CREATE TABLE public.jira_issue
(
    id              BIGINT  PRIMARY KEY     NOT NULL    DEFAULT nextval('jira_issue_seq'),
    jira_sprint_id  BIGINT                  NOT NULL    REFERENCES public.release_info_jira_sprint(id),
    issue_id        BIGINT                  NOT NULL,
    key             TEXT                    NOT NULL,
    description     TEXT,
    icon_url        TEXT,
    name            TEXT,
    browser_url     TEXT
);

-- To add database support for another tool:
-- 1) create table public.<product_name>_info with necessary columns
-- 2) add reference public.<product_name>_info(id) to table public.product
-- 3) create table public.release_info_<product_name> with necessary columns
-- 4) add reference public.release_info_<product_name>(id) to table public.release_info

-- TODO add created, modified, created_by and modified_by to public.sq_info, public.product, public.user_product_claims
