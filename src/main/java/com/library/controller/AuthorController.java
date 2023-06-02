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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAllAuthors(@RequestParam Integer page, @RequestParam Integer sizeOnPage){
        return authorService.findAllWithPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") Long authorId){
        return authorService.findById(authorId);
    }

    @PostMapping
    public AuthorDto saveAuthor(@RequestBody AuthorDto authorDto){
        return authorService.saveOrUpdate(authorDto);
    }

    @PutMapping
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto){
        return authorService.saveOrUpdate(authorDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long authorId){
        authorService.deleteById(authorId);
        return new ResponseEntity<>("Author is deleted successfully!", HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/delete")
    public AuthorDto softDeleteAuthor(@RequestBody AuthorDto authorDto){
        return authorService.softDelete(authorDto);
    }
}
