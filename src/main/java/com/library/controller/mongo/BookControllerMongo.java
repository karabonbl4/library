package com.library.controller.mongo;

import com.library.model.dto.BookDto;
import com.library.model.entity.mongo.Book;
import com.library.service.mongo.BookMongoService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/storage")
public class BookControllerMongo {

    private final BookMongoService bookMongoService;

    @GetMapping
    public List<Book> getAllBooksFromStorage(@Valid @RequestParam Integer page, @Valid @RequestParam Integer sizeOnPage) {
        return bookMongoService.findAllPageable(page, sizeOnPage);
    }

    @GetMapping(value = "/{id}")
    public Book getBookById(@PathVariable ObjectId id) {
        return bookMongoService.findById(id);
    }

    @PostMapping(value = "/deposit/{id}")
    public Book moveToStorage(@PathVariable(value = "id") Long bookId) {
        return bookMongoService.moveToStorage(bookId);
    }

    @DeleteMapping(value = "/recovery")
    public BookDto recoveryFromStorage(@RequestBody ObjectId bookId){
        return bookMongoService.recovery(bookId);
    }
}
