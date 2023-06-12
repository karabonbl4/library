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

    private Author testAuthor;

    private Book testBook;

    private Publisher testPublisher;

    private BookDto bookDto;

    private List<Author> testAuthors;
    private List<Book> testBooks;
    private List<Publisher> testPublishers;

    public TestStorage(){
        testAuthor = new Author();
        testAuthor.setId(1L);
        testAuthor.setName("Name");
        testAuthor.setSurname("Surname");
        testAuthor.setBirthDay(LocalDate.of(1999, 5, 17));
        testAuthor.setDeleted(false);

        testPublisher = new Publisher();
        testPublisher.setId(1L);
        testPublisher.setDeleted(false);
        testPublisher.setTitle("Printing house");
        testPublisher.setCountry("Republic of Belarus");
        testPublisher.setCity("Grodno");
        testPublisher.setStreet("Gorkogo");
        testPublisher.setBuildNumber("12A");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setDeleted(false);
        testBook.setTitle("War and peace");
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
        bookDto.setDeleted(false);
        bookDto.setTitle("War and peace");
        bookDto.setAuthorsFullName(List.of("Alexandr Pushkin"));
        bookDto.setStack(1);
        bookDto.setUnit("A");
    }

}
