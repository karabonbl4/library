CREATE TABLE reference_book (
	book_id                  int8 NOT NULL,
	reference_id             int8 NOT NULL,
	CONSTRAINT reference_book_id_pk PRIMARY KEY (book_id, reference_id),
	CONSTRAINT book_br_id_fk FOREIGN KEY (book_id) REFERENCES public.book(id) ON UPDATE CASCADE,
	CONSTRAINT reference_br_id_fk FOREIGN KEY (reference_id) REFERENCES public.book(id)ON UPDATE CASCADE
);

WITH subquery AS (SELECT ab.book_id, ab.author_id
				  FROM author_book ab
				  JOIN book b ON b.id = ab.book_id)
SELECT s1.book_id, s2.book_id FROM subquery s1
JOIN subquery s2 ON s2.author_id = s1.author_id
WHERE s2.book_id != s1.book_id
ORDER BY s1.book_id