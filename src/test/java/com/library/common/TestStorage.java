package com.library.common;

import com.library.model.entity.Author;
import com.library.model.entity.Book;
import com.library.model.entity.Publisher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class TestStorage {

    private final Author author;

    private final Book book;

    private final Book newBook;

    private final Publisher publisher;

    private final List<Author> authors;

    private final List<Book> books;

    public TestStorage(){
        author = new Author();
        author.setId(1L);
        author.setName("Name");
        author.setSurname("Surname");
        author.setBirthDate(LocalDate.of(1999, 5, 17));

        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setTitle("Printing house");
        publisher.setCountry("Republic of Belarus");
        publisher.setCity("Grodno");
        publisher.setStreet("Gorkogo");
        publisher.setBuildingNumber("12A");

        book = new Book();
        book.setId(1L);
        book.setTitle("War and peace");
        book.setPublicationYear(Short.parseShort("2010"));
        book.setPublisher(publisher);
        book.setStack(1);
        book.setUnit("A");
        book.setAuthors(List.of(author));

        newBook = SerializationUtils.clone(book);
        newBook.setId(null);

        author.setBooks(List.of(book));
        publisher.setBooks(List.of(book));

        authors = new ArrayList<>(List.of(author, author, author));
        books = new ArrayList<>(List.of(book, book, book));
    }
}
