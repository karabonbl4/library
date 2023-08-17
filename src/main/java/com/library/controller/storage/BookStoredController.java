package com.library.controller.storage;

import com.library.model.dto.BookDto;
import com.library.model.dto.BookStoredDto;
import com.library.model.dto.BookStoredTitleDto;
import com.library.service.storage.BookStoredService;
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
public class BookStoredController {

    private final BookStoredService bookStoredService;

    @GetMapping
    public List<BookStoredTitleDto> getAllBooksFromStorage(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return bookStoredService.findAllPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    public BookStoredDto getBookById(@PathVariable String id) {
        return bookStoredService.findById(id);
    }

    @PostMapping(value = "/deposit/{id}")
    public BookStoredDto moveToStorage(@PathVariable(value = "id") Long bookId) {
        return bookStoredService.moveToStorage(bookId);
    }

    @DeleteMapping(value = "/recovery/{id}")
    public BookDto recoveryFromStorage(@PathVariable String id){
        return bookStoredService.recovery(id);
    }
}
