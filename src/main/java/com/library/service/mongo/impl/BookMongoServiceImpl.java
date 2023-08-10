package com.library.service.mongo.impl;

import com.library.model.dto.BookDto;
import com.library.model.entity.mongo.Book;
import com.library.model.mapper.BookMapper;
import com.library.repository.mongo.BookRepositoryMongo;
import com.library.service.BookService;
import com.library.service.mongo.BookMongoService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMongoServiceImpl implements BookMongoService {

    private final BookRepositoryMongo repositoryMongo;

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Override
    public List<Book> findAllPageable(Integer page, Integer countOnPage) {
        Pageable paging = PageRequest.of(page, countOnPage);
        return repositoryMongo.findAll(paging).stream()
                .collect(Collectors.toList());
    }

    @Override
    public Book findById(ObjectId id) {
        return repositoryMongo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public Book moveToStorage(Long bookSqlId) {
        Book book = bookMapper.mapToMongoBook(bookService.findById(bookSqlId));
        bookService.hardDelete(bookSqlId);
        return repositoryMongo.save(book);
    }

    @Transactional
    @Override
    public BookDto recovery(ObjectId bookId) {
        BookDto bookDto = bookMapper.mapFromMongoBook(repositoryMongo.findById(bookId).orElseThrow(EntityNotFoundException::new));
        repositoryMongo.deleteById(bookId);
        return bookService.save(bookDto);
    }
}
