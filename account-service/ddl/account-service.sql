CREATE USER "account_service_user" WITH PASSWORD 'TiP22r1nJyyu';
CREATE DATABASE "account_service" WITH OWNER = "account_service_user";
\c "account_service"
CREATE SCHEMA app;
CREATE TABLE app.account
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_update timestamp without time zone NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    title character varying NOT NULL,
    description character varying NOT NULL,
	balance numeric(12, 2) NOT NULL DEFAULT 0,
    "type" character varying NOT NULL,
    currency uuid NOT NULL,
    PRIMARY KEY (uuid)
);
CREATE TABLE app.operation
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
	date timestamp(3) without time zone NOT NULL,
    description character varying NOT NULL,
	category uuid NOT NULL,
    value numeric(12, 2) NOT NULL,
    currency uuid NOT NULL,
	account uuid NOT NULL,
    PRIMARY KEY (uuid)
);