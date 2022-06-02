CREATE USER "mail_service_user" WITH PASSWORD 'kswDW9g1dbfA';
CREATE DATABASE "mail_service" WITH OWNER = "mail_service_user";
\c "mail_service"
CREATE SCHEMA app;
CREATE TABLE app.mail
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    "from" character varying NOT NULL,
    "to" character varying NOT NULL,
    subject character varying NOT NULL,
    text character varying NOT NULL,
    dt_send timestamp without time zone NOT NULL,
    PRIMARY KEY (uuid)
);