package com.library.common;

import com.library.model.dto.BookDto;
import com.library.model.entity.Author;
import com.library.model.entity.Book;
import com.library.model.entity.Publisher;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TestStorage {

    private final Author author;

    private final Book book;

    private final Book newBook;

    private final Publisher publisher;

    private final BookDto bookDto;

    private final List<Author> authors;

    private final List<Book> books;

    private final List<Publisher> publishers;

    private final List<BookDto> bookDtos;

    public TestStorage(){
        author = new Author();
        author.setId(1L);
        author.setName("Name");
        author.setSurname("Surname");
        author.setBirthDay(LocalDate.of(1999, 5, 17));

        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setTitle("Printing house");
        publisher.setCountry("Republic of Belarus");
        publisher.setCity("Grodno");
        publisher.setStreet("Gorkogo");
        publisher.setBuildNumber("12A");

        book = new Book();
        book.setId(1L);
        book.setTitle("War and peace");
        book.setPublicationYear(Short.parseShort("2010"));
        book.setPublisher(publisher);
        book.setStack(1);
        book.setUnit("A");
        book.setAuthors(List.of(author));

        newBook = new Book();
        newBook.setTitle("War and peace");
        newBook.setPublicationYear(Short.parseShort("2010"));
        newBook.setPublisher(publisher);
        newBook.setStack(1);
        newBook.setUnit("A");
        newBook.setAuthors(List.of(author));

        author.setBooks(List.of(book));
        publisher.setBooks(List.of(book));

        authors = new ArrayList<>(List.of(author, author, author));
        books = new ArrayList<>(List.of(book, book, book));
        publishers = new ArrayList<>(List.of(publisher, publisher, publisher));

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("War and peace");
        bookDto.setPublicationYear(Short.parseShort("2010"));
        bookDto.setStack(1);
        bookDto.setUnit("A");

        bookDtos = new ArrayList<>(List.of(bookDto, bookDto, bookDto));
    }
}
