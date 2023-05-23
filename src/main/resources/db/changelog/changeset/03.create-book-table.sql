CREATE TABLE IF NOT EXISTS book (
	id                  BIGSERIAL,
	title               VARCHAR(256) UNIQUE NOT NULL,
	publication_year    SMALLINT NOT NULL,
	stack               INT NOT NULL,
	unit                VARCHAR(64),
	publisher_id        BIGINT NOT NULL,
	deleted         BOOLEAN DEFAULT FALSE,
	CONSTRAINT book_id_pk PRIMARY KEY (id),
	CONSTRAINT book_publisher_fk FOREIGN KEY (publisher_id)
	    REFERENCES publisher(id) ON UPDATE CASCADE
)