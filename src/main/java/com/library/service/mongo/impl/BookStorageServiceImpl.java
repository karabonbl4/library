package com.library.service.mongo.impl;

import com.library.model.dto.BookDto;
import com.library.model.entity.mongo.StoredBook;
import com.library.model.mapper.BookMapper;
import com.library.repository.mongo.BookRepositoryMongo;
import com.library.service.BookService;
import com.library.service.mongo.BookStorageService;
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
public class BookStorageServiceImpl implements BookStorageService {

    private final BookRepositoryMongo repositoryMongo;

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Override
    public List<StoredBook> findAllPageable(Integer page, Integer countOnPage) {
        Pageable paging = PageRequest.of(page, countOnPage);
        return repositoryMongo.findAll(paging).stream()
                .collect(Collectors.toList());
    }

    @Override
    public StoredBook findById(ObjectId id) {
        return repositoryMongo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public StoredBook moveToStorage(Long bookSqlId) {
        StoredBook storedBook = bookMapper.mapToMongoBook(bookService.findById(bookSqlId));
        bookService.deleteById(bookSqlId);
        return repositoryMongo.save(storedBook);
    }

    @Transactional
    @Override
    public BookDto recovery(ObjectId bookId) {
        BookDto bookDto = bookMapper.mapFromMongoBook(repositoryMongo.findById(bookId).orElseThrow(EntityNotFoundException::new));
        repositoryMongo.deleteById(bookId);
        return bookService.save(bookDto);
    }
}
