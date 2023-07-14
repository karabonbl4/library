package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.model.dto.ResponseMessage;
import com.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.library.constant.ApplicationConstant.BOOK_IS_DELETED;
import static com.library.constant.ApplicationConstant.DATE_TIME_FORMATTER;

/**
 * The class provides endpoints for working with the book entity.
 * @author Lesha Korobko
 */
@RestController
@Slf4j
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * @param page requested page, must not be negative
     * @param sizeOnPage number of elements on page, must be greater than 0
     * @return a list of elements according to the number and order specified in the parameters
     */
    @GetMapping
    public List<BookDto> getAllBooks(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        List<BookDto> books = bookService.findAllWithPageable(page, sizeOnPage);
        log.debug(LocalDateTime.now().format(DATE_TIME_FORMATTER).concat(" - ").concat(String.format("BookController.getAllBooks(%s, %s) - ", page, sizeOnPage)).concat(books.toString()));
        return books;
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @return BookDto with requested id
     */
    @GetMapping(value = "/{id}")
    public BookDto getBookById(@Valid @PathVariable("id") Long bookId) {
        BookDto book = bookService.findById(bookId);
        log.debug(LocalDateTime.now().format(DATE_TIME_FORMATTER).concat(" - ").concat(String.format("BookController.getBookById(%s) - ", bookId)).concat(book.toString()));
        return book;
    }

    /**
     * @param bookDto an instance of the BookDto class to save in the database,
     *                must not contain null fields: authors, publisher
     * @return saved bookDto
     */
    @PostMapping
    public ResponseEntity<BookDto> saveBook(@Valid @RequestBody BookDto bookDto) {
        BookDto book = bookService.saveOrUpdate(bookDto);
        log.debug(LocalDateTime.now().format(DATE_TIME_FORMATTER).concat(" - ").concat(String.format("BookController.saveBook(%s) - ", book)).concat(book.toString()));
        return ResponseEntity.ok(book);
    }

    /**
     * @param bookDto an instance of the BookDto class to update changed fields,
     *                must not contain null fields: authors, publisher
     * @return updated instance of BookDto
     */
    @PutMapping
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookDto bookDto) {
        BookDto book = bookService.saveOrUpdate(bookDto);
        log.debug(LocalDateTime.now().format(DATE_TIME_FORMATTER).concat(" - ").concat(String.format("BookController.updateBook(%s) - ", book)).concat(book.toString()));
        return ResponseEntity.ok(book);
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @return message about result of deleting with http status
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseMessage> softDeleteBook(@Valid @PathVariable(name = "id") Long bookId) {
        bookService.softDelete(bookId);
        ResponseMessage responseMessage = new ResponseMessage(BOOK_IS_DELETED);
        log.debug(LocalDateTime.now().format(DATE_TIME_FORMATTER).concat(" - ").concat(String.format("BookController.softDeleteBook(%s) - ", bookId)).concat(responseMessage.getMessage()));
        return ResponseEntity.ok(responseMessage);
    }
}
