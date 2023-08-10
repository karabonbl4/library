package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.model.dto.ResponseMessage;
import com.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

import static com.library.constant.ApplicationConstant.BOOK_IS_DELETED;

/**
 * The class provides endpoints for working with the book entity.
 * @author Lesha Korobko
 */
@RestController
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
        return bookService.findAllWithPageable(page, sizeOnPage);
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @return BookDto with requested id
     */
    @GetMapping(value = "/{id}")
    public BookDto getBookById(@Valid @PathVariable("id") Long bookId) {
        return bookService.findById(bookId);
    }

    /**
     * @param bookDto an instance of the BookDto class to save in the database,
     *                must not contain null fields: authors, publisher
     * @return saved bookDto
     */
    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.saveOrUpdate(bookDto));
    }

    /**
     * @param bookDto an instance of the BookDto class to update changed fields,
     *                must not contain null fields: authors, publisher
     * @return updated instance of BookDto
     */
    @PutMapping
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.saveOrUpdate(bookDto));
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @return message about result of deleting with http status
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseMessage> softDeleteBook(@Valid @PathVariable(name = "id") Long bookId) {
        bookService.softDelete(bookId);
        return ResponseEntity.ok(new ResponseMessage(BOOK_IS_DELETED));
    }
}
