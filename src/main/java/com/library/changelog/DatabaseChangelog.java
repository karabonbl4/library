package com.library.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.library.model.entity.BookStored;
import com.library.repository.mongo.BookStoredRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "library", author = "Lesha Korobko")
    public void setInventoryNumber(BookStoredRepository bookRepository) {
        List<BookStored> allBooks = bookRepository.findAll();
        for (int i = 1; i <= allBooks.size(); i++) {
            allBooks.get(i - 1).setInventoryNumber("s" + i);
        }
        bookRepository.saveAll(allBooks);
    }

    @ChangeSet(order = "002", id = "bookStored", author = "Lesha Korobko")
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
}
