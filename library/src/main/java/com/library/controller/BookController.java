package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/{id}")
    public BookDto getBookById(@PathVariable("id") Long bookId){
        return bookService.findById(bookId);
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto bookDto){
        return new ResponseEntity<>(bookService.saveOrUpdate(bookDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto){
        return new ResponseEntity<>(bookService.saveOrUpdate(bookDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId){
        bookService.deleteById(bookId);
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity<BookDto> softDeleteBook(@RequestBody BookDto bookDto){
        return new ResponseEntity<>(bookService.saveOrUpdate(bookDto), HttpStatus.ACCEPTED);
    }
}
