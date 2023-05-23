CREATE TABLE IF NOT EXISTS publisher (
	id              BIGSERIAL,
	title           VARCHAR(128) UNIQUE NOT NULL,
	country         VARCHAR(128) NOT NULL,
	city            VARCHAR(128) NOT NULL,
	street          VARCHAR(128) NOT NULL,
	build_num       VARCHAR(128) NOT NULL,
	deleted         BOOLEAN DEFAULT FALSE,
	CONSTRAINT publisher_id_pk PRIMARY KEY (id)
)