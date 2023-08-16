package com.library.service.mongo.impl;

import com.library.model.dto.BookDto;
import com.library.model.document.BookStored;
import com.library.model.dto.BookStoredDto;
import com.library.model.dto.BookStoredTitleDto;
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
    public List<BookStoredTitleDto> findAllPageable(Integer page, Integer countOnPage) {
        Pageable paging = PageRequest.of(page - 1, countOnPage);
        return repositoryMongo.findAll(paging).stream()
                .map(bookMapper::mapToBookStoredTitleDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookStoredDto findById(String id) {
        BookStored bookStored = repositoryMongo.findById(new ObjectId(id)).orElseThrow(EntityNotFoundException::new);
        return bookMapper.mapToBookStoredDto(bookStored);
    }

    @Transactional
    @Override
    public BookStoredDto moveToStorage(Long bookSqlId) {
        BookStored bookStored = bookMapper.mapToMongoBook(bookService.findById(bookSqlId));
        bookService.deleteById(bookSqlId);
        BookStored saveBookStored = repositoryMongo.save(bookStored);
        return bookMapper.mapToBookStoredDto(saveBookStored);
    }

    @Transactional
    @Override
    public BookDto recovery(String bookId) {
        ObjectId objectId = new ObjectId(bookId);
        BookDto bookDto = bookMapper.mapBookStoredToBookDto(repositoryMongo.findById(objectId).orElseThrow(EntityNotFoundException::new));
        repositoryMongo.deleteById(objectId);
        return bookService.save(bookDto);
    }
}
