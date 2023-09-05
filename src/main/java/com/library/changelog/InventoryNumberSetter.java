package com.library.changelog;

import com.library.model.entity.BookStored;
import com.library.repository.mongo.BookStoredRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ChangeUnit(order = "001", id = "inventoryNumber", author = "Lesha Korobko")
@Slf4j
public class InventoryNumberSetter {

    @Execution
    public void setInventoryNumber(BookStoredRepository bookRepository) {
        List<BookStored> allBooks = bookRepository.findAll();
        for (int i = 1; i <= allBooks.size(); i++) {
            allBooks.get(i - 1).setInventoryNumber("s" + i);
        }
        bookRepository.saveAll(allBooks);
    }

    @RollbackExecution
    public void rollback() {
        log.info("Rollback for method setInventoryNumber");
    }
}