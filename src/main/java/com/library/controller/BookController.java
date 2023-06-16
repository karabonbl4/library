package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.service.BookService;
import jakarta.websocket.server.PathParam;
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

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks(@RequestParam Integer page, @RequestParam Integer sizeOnPage) {
        return bookService.findAllWithPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    public BookDto getBookById(@PathVariable("id") Long bookId) {
        return bookService.findById(bookId);
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.save(bookDto));
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(bookDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> softDeleteBook(@PathVariable(name = "id") Long bookId) {
        bookService.softDelete(bookId);
        return ResponseEntity.ok("Book is deleted successfully!");
    }
}
