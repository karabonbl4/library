CREATE TABLE IF NOT EXISTS author_book (
	author_id           BIGINT NOT NULL,
	book_id             BIGINT NOT NULL,
	CONSTRAINT author_book_id_pk PRIMARY KEY (author_id, book_id),
	CONSTRAINT author_id_fk FOREIGN KEY (author_id)
	    REFERENCES author(id),
	CONSTRAINT book_id_fk FOREIGN KEY (book_id)
	    REFERENCES book(id)
)