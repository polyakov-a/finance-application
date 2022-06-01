CREATE USER "classifier_service_user" WITH PASSWORD 'kswDW9g1dbfA';
CREATE DATABASE "classifier_service" WITH OWNER = "classifier_service_user";
\c "classifier_service"
CREATE SCHEMA app;
CREATE TABLE app.currency
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
	dt_update timestamp without time zone NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    title character varying NOT NULL,
    description character varying NOT NULL,
    PRIMARY KEY (uuid)
);

CREATE TABLE app.operation_category
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
	dt_update timestamp without time zone NOT NULL,
    dt_create timestamp without time zone NOT NULL,
    title character varying NOT NULL,
    PRIMARY KEY (uuid)
);