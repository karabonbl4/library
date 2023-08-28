ALTER TABLE book
ADD COLUMN inventory_number VARCHAR(25) UNIQUE;

UPDATE book SET inventory_number = CAST(book.id AS VARCHAR(25));

ALTER TABLE book
ALTER COLUMN inventory_number SET NOT NULL;
