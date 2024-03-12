package com.library.service.impl;

import com.library.dto.BookDto;
import com.library.model.entity.BookStored;
import com.library.dto.BookStoredDto;
import com.library.dto.BookStoredTitleDto;
import com.library.model.entity.Book;
import com.library.model.mapper.BookMapper;
import com.library.repository.mongo.BookStoredRepository;
import com.library.repository.postgres.BookRepository;
import com.library.service.BookStoredService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookStoredServiceImpl implements BookStoredService {

    private final BookStoredRepository repositoryMongo;

    private final BookRepository bookRepository;

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

    @Transactional(value = "chainedTransactionManager", rollbackFor = {Exception.class})
    @Override
    public BookStoredDto moveToStorage(Long bookSqlId) {
        BookStored bookStored = bookMapper.mapToBookStored(bookRepository.findById(bookSqlId).orElseThrow(EntityNotFoundException::new));
        bookRepository.deleteById(bookSqlId);
        return bookMapper.mapToBookStoredDto(repositoryMongo.save(bookStored));
    }

    @Transactional(value = "chainedTransactionManager", rollbackFor = {Exception.class})
    @Override
    public BookDto recovery(String bookId) {
        BookStored bookStored = repositoryMongo.findById(new ObjectId(bookId)).orElseThrow(EntityNotFoundException::new);
        Book book = bookMapper.mapBookStoredToBook(bookStored);
        repositoryMongo.deleteById(new ObjectId(bookId));
        return bookMapper.mapToBookDto(bookRepository.save(book));
    }
}
