CREATE TABLE IF NOT EXISTS book (
	id                bigserial NOT NULL,
	title             varchar(256) NOT NULL,
	publication_year  int2 NOT NULL,
	stack             int4 NOT NULL,
    unit              varchar(64) NULL,
	publisher_id      int8 NOT NULL,
	deleted bool      NULL DEFAULT false,
	CONSTRAINT book_id_pk PRIMARY KEY (id),
	CONSTRAINT book_publisher_fk FOREIGN KEY (publisher_id) REFERENCES public.publisher(id)
)