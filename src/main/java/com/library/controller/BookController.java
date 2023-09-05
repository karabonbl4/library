package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.model.dto.BookTitleDto;
import com.library.model.dto.ResponseMessage;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
 *
 * @author Lesha Korobko
 */
@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book management APIs")
public class BookController {

    private final BookService bookService;

    /**
     * @param page       requested page, must not be negative
     * @param sizeOnPage number of elements on page, must be greater than 0
     * @return a list of elements according to the number and order specified in the parameters
     */
    @GetMapping
    @Operation(summary = "Get all books with pagination")
    @ApiResponse(responseCode = "200",
            description = "Found the list of books",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookTitleDto.class)))
    public List<BookTitleDto> getAllBooks(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return bookService.findAllWithPageable(page, sizeOnPage);
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @return BookDto with requested id
     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a book by its id")
    @ApiResponse(responseCode = "200",
            description = "Found the book",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    public BookDto getBookById(@Valid @PathVariable("id") Long bookId) {
        return bookService.findById(bookId);
    }

    /**
     * @param bookDto an instance of the BookDto class to save in the database,
     *                must not contain null fields: authors, publisher
     * @return saved bookDto
     */
    @PostMapping
    @Operation(summary = "Save getting book")
    @ApiResponse(responseCode = "200",
            description = "Save the book",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    public BookDto saveBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    /**
     * @param bookDto an instance of the BookDto class to update changed fields,
     *                must not contain null fields: authors, publisher
     * @return updated instance of BookDto
     */
    @PutMapping
    @Operation(summary = "Update getting book")
    @ApiResponse(responseCode = "200",
            description = "Update the book",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.update(bookDto);
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @return message about result of deleting with http status
     */
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a book by its id")
    @ApiResponse(responseCode = "200",
            description = "Delete the book by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    public ResponseMessage softDeleteBook(@Valid @PathVariable(name = "id") Long bookId) {
        bookService.softDelete(bookId);
        return new ResponseMessage(BOOK_IS_DELETED);
    }
}