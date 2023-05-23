CREATE TABLE IF NOT EXISTS author (
	id              BIGSERIAL,
	name            VARCHAR(128) NOT NULL,
	surname         VARCHAR(128) NOT NULL,
	date_of_birth   DATE NOT NULL,
	date_of_death   DATE,
	deleted         BOOLEAN DEFAULT FALSE,
	CONSTRAINT author_id_pk PRIMARY KEY (id)
)