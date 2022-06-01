CREATE USER "report_service_user" WITH PASSWORD 'CLlOg42MYtKt';
CREATE DATABASE "report_service" WITH OWNER = "report_service_user";
\c "report_service"
CREATE SCHEMA app;
CREATE TABLE app.report
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    status character varying NOT NULL,
	type character varying NOT NULL,
	description character varying NOT NULL,
	params character varying NOT NULL,
    PRIMARY KEY (uuid)
);
CREATE TABLE app.report_date
(
    report_id uuid NOT NULL,
	data bytea NOT NULL,
);