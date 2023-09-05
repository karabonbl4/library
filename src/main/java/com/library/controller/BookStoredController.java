package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.model.dto.BookStoredDto;
import com.library.model.dto.BookStoredTitleDto;
import com.library.service.BookStoredService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/storage")
@Tag(name = "Book storage", description = "BookStored management APIs")
public class BookStoredController {

    private final BookStoredService bookStoredService;

    @GetMapping
    @Operation(summary = "Get all books with pagination")
    @ApiResponse(responseCode = "200",
            description = "Found the list of books",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookStoredTitleDto.class)))
    public List<BookStoredTitleDto> getAllBooksFromStorage(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return bookStoredService.findAllPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a book by its id")
    @ApiResponse(responseCode = "200",
            description = "Found the book",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookStoredDto.class)))
    public BookStoredDto getBookById(@PathVariable String id) {
        return bookStoredService.findById(id);
    }

    @PostMapping(value = "/deposit/{id}")
    @Operation(summary = "Move getting book from library to storage")
    @ApiResponse(responseCode = "200",
            description = "Save the book",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookStoredDto.class)))
    public BookStoredDto moveToStorage(@PathVariable(value = "id") Long bookId) {
        return bookStoredService.moveToStorage(bookId);
    }

    @DeleteMapping(value = "/recovery/{id}")
    @Operation(summary = "Move getting book from storage to library")
    @ApiResponse(responseCode = "200",
            description = "Drop the book from storage",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    public BookDto recoveryFromStorage(@PathVariable String id) {
        return bookStoredService.recovery(id);
    }
}