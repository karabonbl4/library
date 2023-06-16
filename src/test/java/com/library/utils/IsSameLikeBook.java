package com.library.utils;

import com.library.model.entity.Book;
import org.mockito.ArgumentMatcher;

public final class IsSameLikeBook implements ArgumentMatcher<Book> {

    private final Book asBook;

    public IsSameLikeBook(Book asBook) {
        this.asBook = asBook;
    }

    @Override
    public boolean matches(Book book) {
        return (asBook.getId() ==null || asBook.getId().equals(book.getId()) &&
                asBook.getTitle().equals(book.getTitle()) &&
                asBook.getPublicationYear().equals(book.getPublicationYear()) &&
                asBook.getStack().equals(book.getStack()) &&
                asBook.getUnit().equals(book.getUnit()));
    }
}
