package com.library.utils;

import com.library.model.entity.Book;
import com.library.model.entity.ParentEntity;
import org.mockito.ArgumentMatcher;

public class IsSameLikeEntity<T extends ParentEntity> implements ArgumentMatcher<T> {

    private final T t;
    public IsSameLikeEntity(T t) {
        this.t = t;
    }

    @Override
    public boolean matches(T argument) {
        String className = argument.getClass().getSimpleName();
        if ("Book".equals(className)) {
            Book book = (Book) argument;
            Book asBook = (Book) t;
            return (asBook.getId() == null || asBook.getId().equals(book.getId()) &&
                    asBook.getTitle().equals(book.getTitle()) &&
                    asBook.getPublicationYear().equals(book.getPublicationYear()) &&
                    asBook.getStack().equals(book.getStack()) &&
                    asBook.getUnit().equals(book.getUnit()));
        }
        return false;
    }
}
