CREATE USER "mail_scheduler_service_user" WITH PASSWORD 'kswDW9g1dbfA';
CREATE DATABASE "mail_scheduler_service" WITH OWNER = "mail_scheduler_service_user";
\c "mail_scheduler_service"
CREATE SCHEMA app;
CREATE TABLE app.mail
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
	dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    "to" character varying NOT NULL,
    subject character varying NOT NULL,
    text character varying NOT NULL,
    dt_send timestamp without time zone NOT NULL,
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
CREATE TABLE app.scheduled_mail
(
    uuid uuid NOT NULL DEFAULT gen_random_uuid (),
    dt_create timestamp without time zone NOT NULL,
    dt_update timestamp without time zone NOT NULL,
    schedule uuid NOT NULL,
    mail uuid NOT NULL,
    PRIMARY KEY (uuid),
	CONSTRAINT scheduled_mail_schedule_fk FOREIGN KEY (schedule) REFERENCES app.schedule(uuid),
	CONSTRAINT scheduled_mail_mail_fk FOREIGN KEY (mail) REFERENCES app.mail(uuid)
);