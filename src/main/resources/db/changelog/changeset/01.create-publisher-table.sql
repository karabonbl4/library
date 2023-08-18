CREATE TABLE IF NOT EXISTS publisher (
	id              bigserial NOT NULL,
	title           varchar(128) NOT NULL,
	country         varchar(128) NOT NULL,
	city            varchar(128) NOT NULL,
	street          varchar(128) NOT NULL,
	building_number varchar(128) NOT NULL,
	deleted bool NULL DEFAULT false,
	CONSTRAINT publisher_id_pk PRIMARY KEY (id),
	CONSTRAINT publisher_title_key UNIQUE (title)
)