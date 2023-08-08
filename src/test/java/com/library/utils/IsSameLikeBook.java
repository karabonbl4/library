package com.library.utils;

import com.library.model.entity.postgres.Book;
import org.mockito.ArgumentMatcher;

public class IsSameLikeBook implements ArgumentMatcher<Book> {

    private final Book asBook;
    public IsSameLikeBook(Book book) {
        this.asBook = book;
    }

    @Override
    public boolean matches(Book argument) {
        return (asBook.getId() == null || asBook.getId().equals(argument.getId()) &&
                asBook.getTitle().equals(argument.getTitle()) &&
                asBook.getPublicationYear().equals(argument.getPublicationYear()) &&
                asBook.getStack().equals(argument.getStack()) &&
                asBook.getUnit().equals(argument.getUnit()));
    }
}
