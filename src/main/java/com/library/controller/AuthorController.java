package com.library.controller;

import com.library.model.dto.AuthorDto;
import com.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") Long authorId){
        return authorService.findById(authorId);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> saveAuthor(@RequestBody AuthorDto authorDto){
        return new ResponseEntity<>(authorService.saveOrUpdate(authorDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto){
        return new ResponseEntity<>(authorService.saveOrUpdate(authorDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long authorId){
        authorService.deleteById(authorId);
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity<AuthorDto> softDeleteAuthor(@RequestBody AuthorDto authorDto){
        return new ResponseEntity<>(authorService.saveOrUpdate(authorDto), HttpStatus.ACCEPTED);
    }
}
