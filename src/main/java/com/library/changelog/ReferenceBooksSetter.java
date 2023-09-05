package com.library.changelog;

import com.library.model.entity.BookStored;
import com.library.repository.mongo.BookStoredRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ChangeUnit(order = "002", id = "referenceBooks", author = "Lesha Korobko")
@Slf4j
public class ReferenceBooksSetter {

    @Execution
    public void setReferencesBook(BookStoredRepository bookRepository) {
        List<BookStored> allBooks = bookRepository.findAll();
        for (BookStored book : allBooks) {
            List<BookStored> booksByAuthor = bookRepository.findByAuthors_Id(book.getAuthors().stream().findFirst().orElseThrow().getId());
            List<String> collect = new java.util.ArrayList<>(booksByAuthor.stream().map(bookStored -> bookStored.getId().toString()).toList());
            collect.remove(book.getId().toString());
            if (booksByAuthor.size() > 0) {
                book.setReferenceBooks(collect);
                bookRepository.save(book);
            }
        }
    }

    @RollbackExecution
    public void rollback() {
        log.info("Rollback for mongock method setReferencesBook");
    }
}