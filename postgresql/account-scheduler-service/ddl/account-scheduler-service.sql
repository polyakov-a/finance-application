CREATE USER "account_scheduler_service_user" WITH PASSWORD 'P6OaVGkh4TjU';
CREATE DATABASE "account_scheduler_service" WITH OWNER = "account_scheduler_service_user";
\c "account_scheduler_service"
CREATE SCHEMA app;
CREATE TABLE app.operation
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    account uuid NOT NULL,
    description character varying NOT NULL,
    value numeric(12, 2) NOT NULL,
    currency uuid NOT NULL,
    category uuid NOT NULL,
    PRIMARY KEY (uuid)
);
CREATE TABLE app.schedule
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    start_time timestamp without time zone NOT NULL,
    stop_time timestamp without time zone NOT NULL,
    "interval" bigint NOT NULL,
    time_unit character varying NOT NULL,
    PRIMARY KEY (uuid)
);
CREATE TABLE app.scheduled_operation
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    schedule uuid NOT NULL,
    operation uuid NOT NULL,
    PRIMARY KEY (uuid),
	CONSTRAINT scheduled_operation_schedule_fk FOREIGN KEY (schedule) REFERENCES app.schedule(uuid),
	CONSTRAINT scheduled_operation_operation_fk FOREIGN KEY (operation) REFERENCES app.operation(uuid)
);