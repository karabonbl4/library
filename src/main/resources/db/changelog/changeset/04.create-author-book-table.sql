CREATE TABLE IF NOT EXISTS author_book (
	author_id           int8 NOT NULL,
	book_id             int8 NOT NULL,
	CONSTRAINT author_book_id_pk PRIMARY KEY (author_id, book_id),
	CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES public.author(id),
	CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES public.book(id)
)