package com.library.service.mongo.impl;

import com.library.model.dto.BookDto;
import com.library.model.document.BookStored;
import com.library.model.dto.BookStoredDto;
import com.library.model.mapper.BookMapper;
import com.library.repository.mongo.BookStoredRepository;
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

    private final BookStoredRepository repositoryMongo;

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Override
    public List<BookStoredDto> findAllPageable(Integer page, Integer countOnPage) {
        Pageable paging = PageRequest.of(page - 1, countOnPage);
        return repositoryMongo.findAll(paging).stream()
                .map(bookMapper::mapToBookStoredDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookStored findById(ObjectId id) {
        return repositoryMongo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public BookStored moveToStorage(Long bookSqlId) {
        BookStored bookStored = bookMapper.mapToMongoBook(bookService.findById(bookSqlId));
        bookService.deleteById(bookSqlId);
        return repositoryMongo.save(bookStored);
    }

    @Transactional
    @Override
    public BookDto recovery(ObjectId bookId) {
        BookDto bookDto = bookMapper.mapBookStoredToBookDto(repositoryMongo.findById(bookId).orElseThrow(EntityNotFoundException::new));
        repositoryMongo.deleteById(bookId);
        return bookService.save(bookDto);
    }
}
