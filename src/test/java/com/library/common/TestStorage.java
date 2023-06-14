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

    private final Author testAuthor;

    private final Book testBook;

    private final Publisher testPublisher;

    private final BookDto bookDto;

    private final List<Author> testAuthors;

    private final List<Book> testBooks;

    private final List<Publisher> testPublishers;

    private final List<BookDto> bookDtos;

    public TestStorage(){
        testAuthor = new Author();
        testAuthor.setId(1L);
        testAuthor.setName("Name");
        testAuthor.setSurname("Surname");
        testAuthor.setBirthDay(LocalDate.of(1999, 5, 17));

        testPublisher = new Publisher();
        testPublisher.setId(1L);
        testPublisher.setTitle("Printing house");
        testPublisher.setCountry("Republic of Belarus");
        testPublisher.setCity("Grodno");
        testPublisher.setStreet("Gorkogo");
        testPublisher.setBuildNumber("12A");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("War and peace");
        testBook.setPublicationYear(Short.parseShort("2010"));
        testBook.setPublisher(testPublisher);
        testBook.setStack(1);
        testBook.setUnit("A");
        testBook.setAuthors(List.of(testAuthor));

        testAuthor.setBooks(List.of(testBook));
        testPublisher.setBooks(List.of(testBook));

        testAuthors = new ArrayList<>(List.of(testAuthor, testAuthor, testAuthor));
        testBooks = new ArrayList<>(List.of(testBook, testBook, testBook));
        testPublishers = new ArrayList<>(List.of(testPublisher, testPublisher, testPublisher));

        bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("War and peace");
        bookDto.setAuthorsFullName(List.of("Lev Tolstoy"));
        bookDto.setStack(1);
        bookDto.setUnit("A");

        bookDtos = new ArrayList<>(List.of(bookDto, bookDto, bookDto));
    }
}
