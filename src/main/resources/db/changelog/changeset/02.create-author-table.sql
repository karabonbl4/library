CREATE TABLE IF NOT EXISTS author (
	id          bigserial NOT NULL,
	"name"      varchar(128) NOT NULL,
	surname     varchar(128) NOT NULL,
	birth_date  date NOT NULL,
	death_date  date NULL,
	deleted bool NULL DEFAULT false,
	CONSTRAINT author_id_pk PRIMARY KEY (id)
)